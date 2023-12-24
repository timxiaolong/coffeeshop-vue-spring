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
  mounted(){
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
  methods:{
  	deleteUser(id){
  		const res = deleteUser(id);
  		res
  		.then(()=>{
  			alert('删除成功');
  			this.userList.map((item,index)=>{
  				if(item.id===id){
  					this.userList.splice(index,1);
  				}
  			})
  		})
  		.catch((e)=>{
  			alert(e);
  		})
  	},
  	searchUser(){
  		const val = this.$refs.input.value;
  		const res = getSearchUser(val);
  		res
  		.then((data)=>{
  			this.userList = data;
  		})
  		.catch((e)=>{
  			alert(e);
  		})
  	},
  }
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
