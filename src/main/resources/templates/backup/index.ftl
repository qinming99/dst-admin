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
            <span>所有存档 总大小：{{formatFileSize(totalSize)}}</span>
            <el-button @click="getBackupList()" style="margin-left: 20px">刷新</el-button>
            <el-button style="margin-left: 20px" @click="drawer = true">上传存档</el-button>
        </div>
        <el-drawer title="上传存档"
                   :visible.sync="drawer"
                   :with-header="false" size="50%"
                   :before-close="handleClose">
            <el-card>
                <el-upload
                        class="upload-demo"
                        action="/backup/upload"
                        :before-remove="beforeRemove"
                        :on-success="handleSuccess"
                        multiple
                        :limit="3"
                        accept=".tar"
                        :on-exceed="handleExceed"
                        :file-list="fileList">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传tar压缩文件,最大文件100MB,存档文件如何制作请查看向导</div>
                </el-upload>
            </el-card>

        </el-drawer>
        <el-table :data="tableData"  stripe style="width: 100%">
            <el-table-column prop="fileName" label="存档名称" ></el-table-column>
            <el-table-column prop="fileSize" label="文件大小" >
                <template slot-scope="scope">{{formatFileSize(scope.row.fileSize)}}</template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" ></el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button type="text" @click="rename(scope.row)" size="small">重命名</el-button>
                    <el-button type="text" @click="download(scope.row)" size="small">下载</el-button>
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
            drawer: false,
            fileList: [],
            totalSize:0,
        },
        created() {
            this.getBackupList();
        },
        methods: {
            getBackupList() {
                get("/backup/getBackupList").then((data) => {
                    this.tableData = data ? data : [];
                    if (this.tableData.length > 0) {
                        let total = 0;
                        this.tableData.forEach(e => {
                            total += e.fileSize;
                        })
                        this.totalSize = total;
                    }
                })
            },
            rename(val) {
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
            download(val){
                window.location.href="/backup/download?fileName="+val.fileName;
            },
            handleClose(done) {
                this.$confirm('确认存档上传关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            handleSuccess(response, file, fileList) {
                if (response.code === 0){

                }else {
                    this.warningMessage(response.message);
                }
            },
            handleExceed(files, fileList) {
                this.$message.warning('当前限制选择 3 个文件，本次选择了 '+files.length +' 个文件');
            },
            beforeRemove(file, fileList) {
                return this.$confirm('确定移除 '+file.name+' ？');
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
            formatFileSize(value) {
                if (null == value || value == '') {
                    return "0 Bytes";
                }
                let unitArr = new Array("Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB");
                let index = 0;
                let srcSize = parseFloat(value);
                index = Math.floor(Math.log(srcSize) / Math.log(1024));
                let size = srcSize / Math.pow(1024, index);
                size = size.toFixed(2);//保留的小数位数
                return size + unitArr[index];
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
