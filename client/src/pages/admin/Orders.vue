<template>
  <div class="Orders">
    <header class="clear">
  		<span>订单管理</span>
  	</header>
  	<Tag :tagList="tags" @indexChange="changeTag"/>
  	<div class="content">
  		<table class="ordersTable">
	        <thead>
	        	<tr><th>订单号</th><th>用户昵称</th><th>用户id</th><th>商品</th><th>下单时间</th><th>购买数量</th><th>预定最晚出餐</th><th>价格</th><th>订单状态</th><th>操作</th></tr>
	        </thead>
	        <tbody>
	            <tr v-for="(item,index) in orderList" :key="'order'+item.id">
	            	<td>{{item.id}}</td>
	            	<td>{{item.orderusername}}</td>
	            	<td>{{item.orderuserid}}</td>
	            	<td>{{item.orderitemname}}</td>
	            	<td>{{item.ordertime}}</td>
	            	<td>{{item.num}}</td>
	            	<td>{{item.pretime}}</td>
	            	<td>{{item.price}}</td>
	            	<td>{{item.status}}</td>
	                <td><button class="normal" @click="editOrder(item.id)">编辑</button><button class="delete" @click="deleteOrder(item.id)">删除</button></td>
	            </tr>
	        </tbody>
	    </table>
  	</div>
  </div>
</template>

<script>
import {getOrders,deleteOrder} from '../../api/admin';
import Tag from '../../components/Tag';
import axios from "axios";
export default {
  name: 'Orders',
  components:{
  	Tag
  },
  computed:{
  },
  data(){
  	return{
  		tags:['全部','未付款','待制作','待取餐','已完成'],
  		orderList:[]
  	}
  },
  methods:{
  	changeTag(index){
  		// const res = getOrders(index-1);
  		// res
  		// .then((orders)=>{
  		// 	this.orderList = orders;
  		// })
  		// .catch((e)=>{
  		// 	alert(e);
  		// })
      console.log(index)
      axios({
        url:'http://localhost:8080/admin/getOrderByStatus',
        method:'GET',
        params:{
          status:index
        }
      }).then(result =>{
        this.orderList = result.data
        console.log(this.orderList)
        for (let i = 0; i < this.orderList.length; i++) {
          if (this.orderList[i].status === 0){
            this.orderList[i].status = '待付款'
          }else if (this.orderList[i].status === 1){
            this.orderList[i].status = '待制作'
          }else if (this.orderList[i].status === 2){
            this.orderList[i].status = '待取餐'
          }else if (this.orderList[i].status === 3){
            this.orderList[i].status = '已完成'
          }
        }
      })
  	},
  	editOrder(id){
  		this.$router.push('/backstage/orders/'+id)
  	},
  	deleteOrder(id){
  		// const res = deleteOrder(id);
  		// res
  		// .then(()=>{
  		// 	alert('删除成功');
  		// 	this.orderList.map((item,index)=>{
  		// 		if(item.id===id){
  		// 			this.orderList.splice(index,1);
  		// 		}
  		// 	})
  		// })
  		// .catch((e)=>{
  		// 	alert(e);
  		// })
      axios({
        url:'http://localhost:8080/order/deleOrderById',
        method:'DELETE',
        params:{
          id:id
        }
      }).then(result =>{
        if (result){
          this.$notify.success({
            title:'成功',
            message:'删除成功！'
          })
        }else {
          this.$notify.error({
            title:'失败',
            message:'操作失败'
          })
        }
        this.changeTag(0);
      })
  	}
  },
  mounted(){
  	this.changeTag(0);
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.Orders{
	header{
		width: 100%;
		height: 40px;
		line-height: 40px;
		span{
			float: left;
		}
	}
	.content{
		width: 100%;
		background-color: white;
		position: relative;
		top: -3px;
		padding: 5px;
		.ordersTable{
			width: 100%;
			th{
				text-align: center;
			}
			tbody{
				tr{
					td{
						max-width: 100px;
						min-width: 30px;
						text-align: center;
						button{
							display: block;
							overflow: hidden;
							margin-bottom: 5px;
						}
					}
				}
			}
		}
	}
}
</style>
