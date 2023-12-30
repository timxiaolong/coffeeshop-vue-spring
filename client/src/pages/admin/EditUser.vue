<template>
  <div class="EditUser">
  	<header class="clear">
  		<span>用户管理</span>
  		<div><input ref="input" type="text" placeholder="输入搜索用户" /><button @click="searchUser"><i class="iconfont icon-search" /></button></div>
  	</header>
	<table className="userTable">
        <thead>
        	<tr><th>用户ID</th><th>昵称</th><th>是否为VIP</th><th>联系电话</th><th>操作</th></tr>
        </thead>
        <tbody>
            <tr v-for="(item,index) in userList" :key="'user'+item.id">
                <td>{{item.id}}</td>
                <td>{{item.name}}</td>
                <td>{{item.isVip}}</td>
                <td>{{item.phone}}</td>
                <td><button class="delete" @click="deleteUser(item.id)">删除</button></td>
            </tr>
        </tbody>
    </table>


  </div>
</template>

<script>
import {getAllUser,getSearchUser,deleteUser} from '../../api/admin';
import axios from "axios";
export default {
  name: 'EditUser',
  computed:{
  },
  data(){
  	return{
  		userList:[]
  	}
  },
  methods:{
    getAll(){
      axios({
        url:'http://localhost:8080/user/getalluser',
        method:'GET',
      }).then(result =>{
        this.userList = result.data
        console.log(this.userList)
        for (let i = 0; i < this.userList.length; i++) {
          if (this.userList[i].isVip === 0){
            this.userList[i].isVip = "是"
          }else {
            this.userList[i].isVip = "否"
          }
        }
      })
    },
  	deleteUser(id){
      axios({
        url:"http://localhost:8080/user/deleuserbyid",
        method:'DELETE',
        params:{
          id:id
        }
      }).then(result =>{
        if (result.data){
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success'
          });
          this.getAll()
        }else {
          this.$notify({
            title: '出错了',
            message: '删除失败',
            type: 'warning'
          });
        }
      })
  	},
  	searchUser(){
      axios({
        url:'http://localhost:8080/user/getUserByName',
        method:'GET',
        params:{
          username: this.$refs.input.value
        }
      }).then(result =>{
        if (result.data.size === 0){
          this.$notify.error({
            title: '错误',
            message: '未搜到当前用户'
          });
        }else {
          this.userList = result.data
        }
      })

  	},
  },
  mounted(){
    this.getAll()
  },
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.EditUser{
	header{
		width: 100%;
		height: 40px;
		line-height: 40px;
		span{
			float: left;
		}
		div{
			height: 20px;
			float: right;
      input{
        border: none;
        border-bottom: 1px solid #337da4;
        background-color: rgba(0,0,0,0);
        width: 180px;
        padding-left: 10px;
      }
			button{
        position: relative;
        top: -1px;
        border: none;
        background-color: rgba(0,0,0,0);
				i{
					font-size: 17px;
          color:#337da4;
				}
			}
		}
	}
}
</style>
