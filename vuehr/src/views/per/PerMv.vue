<template>
    <div>
        <div style="display: flex; justify-content: space-between">
            <div>

                <el-button
                        icon="el-icon-plus"
                        type="primary"
                        @click="doAdd"
                >
                    添加
                </el-button>
            </div>
        </div>
        <el-table
                :data="list"
                border
                style="width: 100%">
            <el-table-column
                    prop="removeDate"
                    label="调动日期"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="afterDepId"
                    label="调动后部门"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="afterJobId"
                    label="调动后职位"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="reason"
                    label="调动原因">
            </el-table-column>
            <el-table-column
                    prop="remark"
                    label="备注">
            </el-table-column>
            <el-table-column fixed="right" width="200" label="操作">
                <template slot-scope="scope">
                    <el-button
                            @click="doEdit(scope.row)"
                            style="padding: 3px"
                            size="mini"
                    >编辑
                    </el-button
                    >
                    <el-button
                            @click="doDel(scope.row)"
                            style="padding: 3px"
                            size="mini"
                            type="danger"
                    >删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog title="添加员工调薪信息" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="调动后部门" :label-width="formLabelWidth">
                    <el-input v-model="form.beforesalary"  >
                    </el-input>
                </el-form-item>
                <el-form-item label="调动后职位" :label-width="formLabelWidth">
                    <el-input v-model="form.aftersalary">
                    </el-input>
                </el-form-item>

                <el-form-item label="调动日期" :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="form.removeDate"
                            align="right"
                            type="date"
                            placeholder="选择日期"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="调动原因" :label-width="formLabelWidth">
                    <el-input v-model="form.reason" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="备注" :label-width="formLabelWidth">
                    <el-input v-model="form.remark" autocomplete="off" type="textarea"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="doSave">保存</el-button>
            </div>
        </el-dialog>
    </div>

</template>

<script>
    export default {
        name: "PerSalary",
        data() {
            return {
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    },
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },
                list: [],
                dialogFormVisible: false,
                formLabelWidth: '120px',
                form: {},

            }
        },
        created() {
            this.getList();
        },
        activated() {
            this.getList();
        },
        methods: {
            getList() {
                this.loading = true;
                this.getRequest("/per/move/").then(res => {
                    this.loading = false;
                    if (res) {
                        this.list = res;
                        console.log(this.list)
                    }
                })
            },
            doEdit(item) {
                this.form = Object.assign(item, {})
                this.dialogFormVisible = true;
            },
            doDel(item) {
                this.$confirm(
                    "确定删除吗",
                    "提示",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    }
                )
                    .then(() => {
                        this.deleteRequest("/per/move/del/" + item.id).then((res) => {
                            if (res) {
                                this.getList();
                            }
                        });
                    })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消删除",
                        });
                    });

            },
            doAdd() {
                this.reset();
                this.dialogFormVisible = true;

            },
            doSave() {
                let params = {
                    ...this.form
                }
                if (this.form.id) {
                    this.putRequest('/per/move/update/', params).then(res => {
                        if (res) {
                            this.dialogFormVisible = false;
                            this.getList()
                        }
                    })

                } else {

                    this.postRequest('/per/move/add/', params).then(res => {
                        if (res) {
                            this.dialogFormVisible = false;
                            this.getList()
                        }
                    })
                }


            },
            reset() {
                this.form = {
                    afterDepId: "",
                    afterJobId: "",
                    removeDate: "",
                    reason: "",
                    remark: ""
                }
            }
        }
    }
</script>
