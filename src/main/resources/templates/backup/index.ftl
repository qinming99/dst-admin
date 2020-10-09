<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>配置页</title>
    <#include "../common/header.ftl"/>
</head>
<body>

<div id="backup_index_app">
    <el-card>
        <div slot="header" class="clearfix">
            <span>所有存档</span>
        </div>
        <el-table :data="tableData"  stripe style="width: 100%">
            <el-table-column prop="fileName" label="存档名称" ></el-table-column>
            <el-table-column prop="fileSize" label="文件大小(MB)" ></el-table-column>
            <el-table-column prop="createTime" label="创建时间" ></el-table-column>
            <el-table-column label="操作" width="100">
                <template slot-scope="scope">
                    <el-button type="text" @click="rename(scope.row)" size="small">重命名</el-button>
                    <el-button type="text" @click="deleteBackup(scope.row)" style="color:red" size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
</div>

</body>

<script>

    new Vue({
        el: '#backup_index_app',
        data: {
            tableData: {},
        },
        created() {
            this.getBackupList();
        },
        methods: {
            getBackupList() {
                get("/backup/getBackupList").then((data) => {
                    this.tableData = data;
                })
            },
            rename(val) {
                console.log(val)
                this.$prompt('新名称', '修改名称', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({value}) => {
                    get("/backup/rename", {fileName: val.fileName,newFileName:value}).then((data) => {
                        this.successMessage("修改成功");
                        this.getBackupList();
                    })
                }).catch(() => {

                });
            },
            deleteBackup(val){
                this.$confirm('确认删除:'+val.fileName+', 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    get("/backup/deleteBackup", {fileName: val.fileName}).then((data) => {
                        this.successMessage("删除成功");
                        this.getBackupList();
                    })
                }).catch(() => {

                });
            },
            successMessage(message) {
                this.$message({
                    message: message,
                    type: 'success'
                });
            },
            warningMessage(message) {
                this.$message({
                    message: message,
                    type: 'warning'
                });
            },
        }
    });


</script>

</html>
