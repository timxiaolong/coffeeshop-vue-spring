<template>
  <div class="Cart">
    <div v-if="orderList.length>0">
      <div class="cartTableHeader">
        <span>商品信息</span>
        <span>单价</span>
        <span>数量</span>
        <span>小计</span>
        <span>交易操作</span>
      </div>
      <ul class="orderList">
        <li v-for="(item,index) in orderList" :key="'order'+item.id">
          <div class="orderDetail">
            <img :src=item.picture alt="商品图片" />
            <div class="goodsName" style="text-align: center">
              <p @click="navTo('/mall/goods/'+item.goods.id)">{{item.itemname}}</p>
            </div>
            <span class="unitPrice">{{'￥'+item.price}}</span>
            <span class="num">
              <NumberInput
                @changeHandle="numberChange(item.id)"
                :initNum="item.num"
                v-model="item.num"
                :min="1"
                :max="999"
              />
            </span>
            <!-- <input @change="numberChange(item.id)" type="text" v-model="item.temGoodsNum" min="1" class="numInput" /> -->
            <span class="amount">{{'￥'+item.amount}}</span>
            <button @click="deleteOrder(item.id)">删除</button>
          </div>
        </li>
      </ul>
      <div class="cartFooter">
        <span>应付金额：</span>
        <span class="total">{{'￥'+totalAmount}}</span>
        <button @click="settleAccounts">下单</button>
      </div>
    </div>
    <p class="emptyTips" v-else>购物车还是空滴~</p>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import {getOrderByState,deleteOrder,settleAccounts} from '../../api/client';
import NumberInput from '../../components/NumberInput';
import axios from "axios";
import {id} from "html-webpack-plugin/lib/chunksorter";

export default {
  name: 'Cart',
  components:{
    NumberInput
  },
  computed:{
    ...mapState([
      'clientToken'
    ]),
    totalAmount(){
      let amount = 0;
      this.orderList.map((item,index)=>{
        amount+=item.amount;
      })
      return amount;
    }
  },
  data () {
    return {
      orderList:[],
    }
  },

  methods:{
    getCarft(){
      console.log(localStorage.getItem('id'))
      axios({
        url:'http://localhost:8080/shoppingcarft/getcarft',
        method:'GET',
        params:{
          userid: Number(localStorage.getItem('id'))
        }
      }).then(result =>{
        this.orderList = result.data
        console.log(this.orderList)
      })
    },
    numberChange(orderId){
      this.orderList.map((item,index)=>{
        if(orderId===item.id){
          item.amount = item.num*item.price;
          console.log(item.temGoodsNum,item.goods.unitPrice)
        }
      })
    },
    deleteOrder(orderId){
      // const res = deleteOrder(orderId);
      // res
      // .then(()=>{
      //   alert('删除订单成功！');
      //   this.orderList.map((item,index)=>{
      //     if(item.id===orderId){
      //       this.orderList.splice(index,1);
      //     }
      //   })
      // })
      // .catch((e)=>{
      //   alert(e);
      // })
      axios({
        url:'http://localhost:8080/shoppingcarft/deleteById',
        method:'DELETE',
        params:{
          id:orderId
        }
      }).then(result =>{
        if (result){
          this.$notify.success({
            title:'成功',
            message:'成功删除一条数据'
          })
          this.getCarft();

        }else {
          this.$notify.error({
            title:'失败',
            message:'删除失败，请重试'
          })
        }
      })
    },
    navTo(route){
      this.$router.push(route);
    },
    cleanCarft(id){
      axios({
        url:'http://localhost:8080/shoppingcarft/clean',
        method:'DELETE',
        params:{
          userId:id
        }
      })
    },
    settleAccounts(){
      let cartList = [];
      this.orderList.map((item,index)=>{
        cartList.push({
          // id:item.id,
          orderusername:localStorage.getItem('username'),
          orderuserid:localStorage.getItem('id'),
          orderitemid:item.itemid,
          orderitemname:item.itemname,
          price:item.price,
          num:item.num,
          amount:item.price * item.temGoodsNum,
          picture:item.picture
        })
      });
      axios({
        url:'http://localhost:8080/order/sendOrders',
        method:'POST',
        data:cartList
      }).then(result =>{
        if (result){
          this.$message.success('下单成功，请到订单查看')
          this.cleanCarft(localStorage.getItem('id'))
        }else {
          this.$message.error('下单失败，请联系管理员')
        }
        this.orderList=[]
      })
    }
  },

  mounted(){
    this.getCarft();
  },
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.Cart{
  width: 100%;
  .emptyTips{
      width: 200px;
      text-align: center;
      display: block;
      margin: 30px auto;
      color:@thirdColor;
      font-size: 20px;
  }
  .cartTableHeader{
    width: 100%;
    height: 40px;
    background-color: #f5f5f5;
    border: 1px solid @borderColor;
    color: @fontDefaultColor;
    font-size: 14px;
    line-height: 40px;
    span{
      display: inline-block;
      width: 14%;
      &:first-child{
        width:40%;
        text-align: center;
      }
    }
  }
  .orderList{
    width: 100%;
    li{
      border: 1px solid @borderColor;
      border-top: none;
      font-size: 13px;
      .orderHeader{
        background-color: #f1f1f1;
        height: 40px;
        line-height: 40px;
        padding: 0 5px;
        .orderTime{
          font-weight: 600;
        }
        .orderId,.state{
          margin-left: 10px;
        }
      }
      .orderDetail{
        width: 100%;
        padding: 10px;
        position: relative;
        overflow: hidden;
        img{
          width: 84px;
          height: 84px;
          display: inline-block;
        }
        .goodsName{
          display: inline-block;
          margin-left: 5px;
          width: 230px;
          vertical-align: top;
          p{
            cursor: pointer;
            line-height: 20px;
            &:hover{
              text-decoration:underline;
            }
          }
          span{
            color:@fontDefaultColor;
            display: block;
            margin-top: 10px;
          }
        }
        .unitPrice,.num,.amount{
          display: inline-block;
          vertical-align: top;
          width: 15%;
          height: 85px;
          line-height: 85px;
          text-align: center;
        }
        .NumberInput{
          position: relative;
          top: 25px;
        }
        button{
          position: absolute;
          right: 90px;
          bottom: 40px;
          width: 70px;
          height: 30px;
          border-radius: 3px;
          background-color: @falseColor;
          color:white;
          border: none;

        }
      }
    }
  }
  .cartFooter{
    width: 100%;
    height: 60px;
    line-height: 60px;
    padding: 0 10px;
    border: 1px solid @borderColor;
    border-top: none;
    background-color: #f5f5f5;
    position: relative;
    span{
      color: @fontDefaultColor;
      display: inline-block;
      vertical-align: top;
    }
    .total{
      color:@falseColor;
      font-size: 25px;
      font-weight: 600;
    }
    button{
      position: absolute;
      right: 0;
      top: 0;
      width: 100px;
      height: 100%;
      background-color: @thirdColor;
      border: none;
      color:white;
      font-size: 20px;
    }
  }
}
</style>
