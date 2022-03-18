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
                    prop="eid"
                    label="员工姓名"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="asdate"
                    label="调薪日期"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="beforesalary"
                    label="调整前薪资"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="aftersalary"
                    label="调整后薪资"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="reason"
                    label="调薪原因">
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
        <el-dialog title="新增/编辑" :visible.sync="dialogFormVisible">
            <el-form :model="form">


                <el-form-item label="选择员工" :label-width="formLabelWidth">

                    <el-select v-model="form.eid" placeholder="请选择">
                        <el-option
                                v-for="item in personList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="调整前薪资" :label-width="formLabelWidth">
                    <el-input v-model="form.beforesalary" type="number">
                        <template slot="append">￥</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="调整后薪资" :label-width="formLabelWidth">
                    <el-input v-model="form.aftersalary" type="number">
                        <template slot="append">￥</template>
                    </el-input>
                </el-form-item>

                <el-form-item label="调整日期" :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="form.asdate"
                            align="right"
                            type="date"
                            placeholder="选择日期"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="调整原因" :label-width="formLabelWidth">
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
                personList: [],
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
                this.getRequest("/per/salary/").then(res => {
                    this.loading = false;
                    if (res) {
                        this.list = res;
                        console.log(this.list)
                    }
                })
            },
            doEdit(item) {
                this.getPersonList();
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
                        this.deleteRequest("/per/salary/del/" + item.id).then((res) => {
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
                this.getPersonList();
                this.dialogFormVisible = true;

            },
            doSave() {
                let params = {
                    ...this.form
                }
                if (this.form.id) {
                    this.putRequest('/per/salary/update/', params).then(res => {
                        if (res) {
                            this.dialogFormVisible = false;
                            this.getList()
                        }
                    })

                } else {

                    this.postRequest('/per/salary/add/', params).then(res => {
                        if (res) {
                            this.dialogFormVisible = false;
                            this.getList()
                        }
                    })
                }


            },
            reset() {
                this.form = {
                    eid: '',
                    aftersalary: "",
                    beforesalary: "",
                    asdate: "",
                    reason: "",
                    remark: ""
                }
            },
            getPersonList() {
                this.loading = true;
                this.getRequest('/employee/basic/').then((res) => {
                    this.loading = false;
                    if (res) {
                        this.personList = res.data;
                        console.log(this.personList)

                    }
                });
            },
        }
    }
</script>
