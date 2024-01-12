<template>
  <div class="EditOrders">
    <header class="clear">
      <span>订单编辑</span>
    </header>
    <div class="content">
      <div class="inputBox">
        <span>订单号：</span>
        <span class="val">{{ id }}</span>
      </div>
      <div class="inputBox">
        <span>商品名称：</span>
        <span class="val">{{ goods }}</span>
      </div>
      <div class="inputBox">
        <span>总金额：</span>
        <span class="val">{{ '¥' + amount }}</span>
      </div>
      <div class="inputBox">
        <span>数量：</span>
        <NumberInput v-model="data.num" :initNum="data.num" :min="1" :max="999"/>
      </div>
      <div class="inputBox">
        <span>订单状态：</span>
        <el-radio-group v-for="(item,index) in states" :key="item.id" v-model="data.status" :initVal="data.status"
                        radioName="state" :radioVal="item.id">
          <el-radio :label=index>{{ item }}</el-radio>
        </el-radio-group>
      </div>
      <div class="btnBox">
        <button class="confirmBtn" @click="saveChange">保存修改</button>
        <button class="normalBtn" @click="back">返回</button>
      </div>
    </div>
  </div>
</template>

<script>
import {getAOrder, changeOrder} from '../../api/admin';
import Radio from '../../components/Radio';
import NumberInput from '../../components/NumberInput';
import axios from "axios";

export default {
  name: 'EditOrders',
  components: {
    Radio,
    NumberInput
  },
  computed: {
    // amount(){
    //   let price = 0;
    //   this.spec.map((item,index)=>{
    //     if(item.id===this.temSpecId){
    //       price = item.unitPrice;
    //     }
    //   })
    //   return this.temNum*price;
    // }
  },
  data() {
    return {
      id: this.$route.params.id,
      goods: '',
      // spec:[],
      states: ['待付款', '已付款，等待制作', '制作完成，等待取餐', '已完成'],
      curSpecId: '',
      curStateId: '',
      temSpecId: '',
      temNum: 0,
      temState: '',
      temStateId: 0,
      amount: '',
      data: {
        num: 0,
        status: 0
      }
    }
  },
  methods: {
    fetchOrderDetail(id) {
      // const res = getAOrder(id);
      // res
      // .then((order)=>{
      //   this.goods = order.goods;
      //   this.spec = order.spec;
      //   this.states = order.states;
      //   this.curSpecId = order.curSpec.id;
      //   this.curStateId = order.curState.id;
      //   this.temSpecId = order.curSpec.id;
      //   this.temNum = Number(order.num);
      //   this.temStateId = order.curState.id;
      // })
      // .catch((e)=>{
      //   alert(e);
      // })
      axios({
        url: 'http://localhost:8080/order/findorderbyid',
        method: 'GET',
        params: {
          id: id
        }
      }).then(result => {
        console.log(result)
        this.data.id = result.data.id
        this.goods = result.data.orderitemname
        this.amount = result.data.price
        this.data.num = result.data.num
        this.data.status = result.data.status
      })
    },

    back() {
      this.$router.go(-1);
    },

    saveChange() {
      console.log(this.data)
      axios({
        url: 'http://localhost:8080/order/changeorder',
        method: 'PUT',
        data: {
          id: this.id,
          num: this.data.num,
          status: this.data.status
        }
      }).then(result => {
        if (result) {
          this.$notify.success({
              title: '成功！',
              message: '修改成功'
            }
          )
        } else {
          this.$notify.error({
            title: '出错了！',
            message: '修改失败'
          })
        }
      })
    }

  },
  mounted() {
    this.fetchOrderDetail(this.$route.params.id);
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.EditOrders {
  header {
    width: 100%;
    height: 40px;
    line-height: 40px;

    span {
      float: left;
    }
  }

  .content {
    width: 100%;
    background-color: white;
    padding: 10px;

    .inputBox {
      margin-bottom: 30px;

      span {
        width: 90px;
        display: inline-block;
        color: @fontDefaultColor;
        font-weight: 600;
      }

      .Radio /deep/ input:checked + .tipsBox {
        &:after {
          background-color: #337da4;
        }
      }

      .tips {
        font-weight: normal;
        width: auto;
        font-size: 13px;
        position: relative;
        left: 3px;
      }

      .val {
        width: auto;
        font-weight: 500;
        font-size: 14px;
        color: @fontDeepColor;
      }

      .NumberInput {
        display: inline-block;
        vertical-align: middle;
      }
    }

    .btnBox {
      margin: auto;
      display: block;
      width: 250px;
    }

    .confirmBtn {
      display: inline-block;
      margin-right: 30px;
      background-color: #337da4;
      color: white;
      border: none;
      width: 100px;
      height: 30px;
      border-radius: 5px;
      cursor: pointer;
    }

    .normalBtn {
      display: inline-block;
      background-color: grey;
      color: white;
      border: none;
      width: 100px;
      height: 30px;
      border-radius: 5px;
      cursor: pointer;
    }
  }
}
</style>
