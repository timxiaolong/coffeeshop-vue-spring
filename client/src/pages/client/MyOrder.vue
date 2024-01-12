<template>
  <div class="MyOrder">
    <ul class="tagList">
      <li :class="{selected:curIndex===index}" v-for="(item,index) in tagList" :key="'tag'+index" @click="changeIndex(index)">
        {{item}}
      </li>
    </ul>
    <div class="orderBox">
      <div class="orderTableHeader">
        <span>商品</span>
        <span>单价</span>
        <span>数量</span>
        <span>实付款</span>
        <span>交易操作</span>
      </div>
      <ul class="orderList">
        <li v-for="(item,index) in orderList" :key="'order'+item.id">
          <div class="orderHeader">
            <span class="orderTime">{{item.ordertime}}</span>
            <span class="orderId">{{'订单号：'+item.id}}</span>
            <span class="state">{{tagList[item.status+1]}}</span>
            <span class="deleteBtn" @click="outerVisible = true;curStatus = item.status;curOrderId = item.id"><i class="iconfont icon-close" /></span>
          </div>
          <div class="orderDetail">
            <img :src="item.picture" alt="商品图片" />
            <div class="goodsName">
              <p style="text-align: center">{{item.orderitemname}}</p>
            </div>
            <span class="unitPrice">{{'￥'+item.price}}</span>
            <span class="num">{{item.num}}</span>
            <span class="amount">{{'￥'+item.price * item.num}}</span>
              <button class="sysbutton" v-if="item.status===0" @click="centerDialogVisible = true">确认付款</button>
              <button class="sysbutton" v-else-if="item.status===2" @click="confirmReceive(item.id)">确认收货</button>
              <button class="sysbutton" v-else-if="item.status===3 && !item.hascomm" @click="dialogFormVisible = true; commid = item.orderitemid; commname = item.orderitemname; orderid = item.id">评价</button>
              <span class="hasComment" v-else-if="item.status===3 && item.hascomm">已评价</span>
            <el-dialog
                title="提示"
                :visible.sync="centerDialogVisible"
                width="50%"
                center
                >
                <div style="align-content: center;text-align: center" class="code ">
                <img  src="../../assets/img/code2.png">
                </div>
                <span  slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirmPay(item.id);centerDialogVisible = false">我已付款</el-button>
              </span>
              </el-dialog>
          </div>
        </li>
      </ul>
    </div>
    <Popup title="商品评价" @popupClose="closePopup" v-show="popupShow">
      <div class="popupContent" slot="popupContent">
        <div class="scoreBox">
          <span class="tips">评分：</span>
          <i
            class="iconfont icon-collection_fill"
            v-for="(item,index) in 5"
            :key="'star'+index"
            :style="{color:(index+1)<=curStar?'#f9bd4f':'white'}"
            @mouseover="setCurStar(index+1)"
            @mouseout="setCurStar(0)"
            @click="confirmStar(index+1)"
          />
        </div>
        <textarea v-model="comment" cols="30" rows="10" placeholder="请输入评论内容"></textarea>
        <button @click="sendComment(orderid)">发表</button>
      </div>
    </Popup>
    <el-dialog title="提示：" :visible.sync="outerVisible">
      <el-dialog
        width="30%"
        title="错误"
        :visible.sync="innerVisible"
        append-to-body>
        <p>非待付款订单无法删除，请咨询前台店员</p>
      </el-dialog>
      <p>确定删除订单？</p>
      <div slot="footer" class="dialog-footer">
        <el-button @click="outerVisible = false">取 消</el-button>
        <el-button type="primary" v-if="curStatus === 0 " @click="deleteOrder(curOrderId)">确定删除</el-button>
        <el-button type="primary" v-if="curStatus !== 0" @click="innerVisible = true">确定删除</el-button>
      </div>
    </el-dialog>
    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="评分" :label-width="formLabelWidth">
          <el-rate v-model="value1" style="padding: 10px 0"></el-rate>
        </el-form-item>
        <el-form-item label="评价" :label-width="formLabelWidth">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="textarea">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false;sendComment(orderid)">确 定</el-button>
      </div>
    </el-dialog>
  </div>


</template>

<script>
import { mapState } from 'vuex';
import {getOrderByState,deleteOrder,confirmReceive,pay,sendComment} from '../../api/client';
import Popup from '../../components/Popup';
import axios from "axios";

export default {
  name: 'MyOrder',
  components:{
    Popup
  },
  computed:{
    ...mapState([
      'clientToken'
    ]),
  },
  data () {
    return {
      tagList:['全部订单','待付款','待制作','制作完成，等待取餐','已完成'],
      curIndex:0,
      orderList:[],
      popupShow:false,
      curOrderId:0,
      curCommentGoodsId:'',
      curCommentGoodsDetailId:'',
      curStar:0,
      hasClickStar:false,
      comment:'',
      centerDialogVisible: false,
      outerVisible:false,
      innerVisible:false,
      dialogFormVisible:false,
      curStatus:0,
      formLabelWidth: '120px',
      textarea:'',
      value1:0,
      commid:0,
      commname : '',
      orderid:0
    }
  },

  methods:{
    changeIndex(i){
      this.curIndex = i;
      this.getOrderByState(this.curIndex-1);
    },
    navTo(route){
      this.$router.push(route);
    },
    getOrderByState(state){
      console.log(state)
    axios({
      url:'http://localhost:8080/order/findorderbystatus',
      method:'GET',
      params:{
        id:localStorage.getItem('id'),
        status:state
      }
    }).then(result =>{
      this.orderList = result.data
      console.log(this.orderList)
    })
    },
    deleteOrder(orderId){
      axios({
        url:'http://localhost:8080/order/deleOrderById',
        method:'DELETE',
        params:{
          id:orderId
        }
      }).then(result =>{
        this.outerVisible = false;
        this.curOrderId = 0
        if (result){
          this.$message.success('订单删除成功')
          this.getOrderByState(-1)
        }else {
          this.$message.error('订单删除失败，请联系前台店员')
        }

      })
    },

    confirmPay(id){
      console.log(id)
      axios({
        url:'http://localhost:8080/order/changeOrderStatus',
        method:'PUT',
        params:{
          id:id,
          status:1
        }
      }).then(result =>{
        if (result){
          this.$message.success('付款成功！')
          this.getOrderByState(-1)
        }else {
          this.$message.error('付款失败，请联系管理员')
        }
      })
    },
    confirmReceive(orderId){
      const res = confirmReceive(orderId);
      res
      .then(()=>{
        alert('确认收货成功！');
        this.orderList.map((item,index)=>{
          if(item.id===orderId){
            item.state = 3;
          }
        })
      })
      .catch((e)=>{
        alert(e);
      })
    },

    closePopup(){
      this.popupShow = false;
      this.curCommentGoodsId = '';
      this.curOrderId = '';
      this.curCommentGoodsDetailId = '';
      this.hasClickStar = false;
      this.curStar = 0;
      this.comment = '';
    },
    showPopup(orderId,goodsId,goodsDetailId){
      // this.curCommentGoodsId = goodsId;
      // this.curOrderId = orderId;
      // this.curCommentGoodsDetailId = goodsDetailId;
      // this.popupShow = true;
    },
    confrimComm(id){
      axios({
        url:'http://localhost:8080/order/confirmComm',
        method:'PUT',
        params:{
          id:id
        }
      }).then(result =>{
        if (!result){
          this.$message.error('评价出错了！')
        }
      })
    },
    sendComment(id){
      if(this.value1<=0 || this.textarea ==''){
        alert('评分和评价不能为空！');
        return;
      }
      axios({
        url:'http://localhost:8080/comment/addComment',
        method:'POST',
        data:{
          id:null,
          userid: localStorage.getItem('id'),
          username:localStorage.getItem('username'),
          itemid: this.commid,
          itemname:this.commname,
          rating: this.value1,
          commentcontent: this.textarea
        }
      }).then(result =>{
        if (result){
          this.dialogFormVisible = false
          this.confrimComm(id)
          this.$message.success('评价成功！')
          this.getOrderByState(-1)
        }else {
          this.$message.error('评价失败，请重试')
        }
      })
    },
    setCurStar(star){
      if(this.hasClickStar){
        return;
      }
      this.curStar = star;
    },
    confirmStar(star){
      this.curStar = star;
      this.hasClickStar = true;
    }
  },


  mounted(){
    this.getOrderByState(-1);
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.MyOrder{
  .tagList{
    li{
      text-align: center;
      display: inline-block;
      font-weight: 550;
      font-size: 18px;
      border-bottom: 2px solid @borderColor;
      cursor: pointer;
      padding: 10px 20px;
      position: relative;
      &:after{
        top:12px;
        right: 0;
        position:absolute;
        content:'';
        width: 1px;
        height: 15px;
        background-color: @borderColor;
      }
      &:last-child{
        &:after{
          display:none;
        }
      }
    }
    .selected{
      color:@thirdColor;
      border-bottom: 2px solid @thirdColor;
    }
  }
  .code{
    img{
      display: block;
      margin: 0 auto;
    }
    span{
      font-size: 12px;
      color:@thirdColor;
      margin-top: 10px;
      display: block;
    }
  }
  .orderBox{
    width: 100%;
    margin-top: 20px;
    .orderTableHeader{
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
        font-size: 13px;
        margin-top: 10px;
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
          .deleteBtn{
            float: right;
            cursor: pointer;
            i{

            }
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
          .unitPrice,.num,.amount,.hasComment{
            display: inline-block;
            vertical-align: top;
            width: 15%;
            height: 85px;
            line-height: 85px;
            text-align: center;
          }
          .sysbutton{
            position: absolute;
            right: 90px;
            bottom: 40px;
            width: 70px;
            height: 30px;
            border-radius: 3px;
            background-color: @thirdColor;
            color:white;
            border: none;
          }
        }
      }
    }
  }
  .popupContent{
    padding: 10px;
    .scoreBox{
      width: 100%;
      height: 50px;
      position: relative;
      line-height: 50px;
      text-align: left;
      .tips{
        font-size: 15px;
        vertical-align: middle;
        display: inline-block;
      }
      i{
        cursor: pointer;
        vertical-align: middle;
        display: inline-block;
        font-size: 25px;
        margin-right: 5px;
        -webkit-text-stroke: 1px #f9bd4f;
      }
    }
    textarea{
      width: 400px;
      height: 80px;
      padding: 5px;
      border:1px solid @borderColor;
      display: block;
      margin-top: 10px;
    }
    button{
      display: block;
      margin: 10px auto;
      width: 70px;
      height: 30px;
      border-radius: 3px;
      background-color: #313541;
      color:white;
      border: none;
    }
  }
}
</style>
