<template>
  <div class="GoodsDetail">
    <div class="content">
      <div class="goodsInfo">
        <img class="infoLeft" :src="goodsImg" alt="商品图片"/>
        <div class="infoRight">
          <div class="infoBox">
            <h3 class="name">{{ goodsName }}</h3>
          </div>
          <div class="infoBox">
            <p>{{ goodsDesc }}</p>
          </div>
          <div class="infoBox">
            <h3 class="price">{{ '¥' + goodsPrice }}</h3>
          </div>
          <div class="infoBox">
            <span>数量：</span>
            <NumberInput v-model="num" :min="1" :max="temStockNum"/>
          </div>
          <button class="buyBtn" @click="centerDialogVisible = true">立即购买</button>
          <button @click="addToCart">加入购物车</button>
        </div>
      </div>
      <section class="msgBox leftContainer">
        <ul class="tagList">
          <li :class="{selected:1}">
            评价 {{commList.size}}
          </li>
        </ul>
        <div class="commentBody" v-for="(item, index) in commList" :key = "index">
          <el-rate
            v-model="item.rating"
            disabled
            show-score
            text-color="#ff9900"
            :score-template="item.rating"
          ></el-rate>
          <p>匿名用户</p><br/>
          <p>{{ item.commentcontent }}</p>
        </div>
      </section>
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
                <el-button type="primary" @click="centerDialogVisible = false; buy">确认已支付</el-button>
              </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex';
import {getGoodsInfo, getGoodsMsg, askGoodsMsg, addOrder, getComment, getGoodsList} from '../../api/client';
import NumberInput from '../../components/NumberInput';
import Radio from '../../components/Radio';
import GoodsItem from '../../components/GoodsItem';
import axios from "axios";
import moment from "moment";

export default {
  name: 'GoodsDetail',
  components: {
    NumberInput,
    Radio,
    GoodsItem
  },
  computed: {
    ...mapState([
      'clientToken',
      'clientName'
    ]),
    id() {
      return this.$route.params.id;
    },
    temStockNum() {
      let stockNum = 0;
      this.specs.map((item, index) => {
        if (item.id === this.temSpecId) {
          stockNum = Number(item.stockNum);
        }
      })
      return stockNum;
    },
    filterList() {
      return this.goodsList.filter((item) => {
        return String(item.id) !== String(this.id);
      })
    }
  },
  data() {
    return {
      goodsImg: '',
      goodsName: '',
      goodsDesc: '',
      goodsPrice: 0,
      specs: [],
      typeId: '',
      temSpecId: 0,
      num: 1,
      msgList: [],
      askContent: '',
      tagList: ['评价'],
      curIndex: 0,
      rate: '',
      commList: [],
      goodsList: [],
      rating:[],
      centerDialogVisible:false
    }
  },

  methods: {
    changeIndex(i) {
      this.curIndex = i;
    },

    getGoodsInfo(id) {
      const id_i = Number(id)
      console.log(id)
      axios({
        url: 'http://localhost:8080/menu/getmenubyid',
        method: 'GET',
        params: {
          id: id_i
        }
      }).then(data => {
        console.log(data.data)
        this.goodsImg = data.data.picture;
        this.goodsName = data.data.name;
        this.goodsDesc = data.data.describtion;
        this.goodsPrice = data.data.price;
        this.typeId = data.data.typeId;
         // this.getTypeGoodsList(data.typeId);
      }).catch(e => {
        console.dir(e)
      })
    },

    getGoodsMsg(id) {
      // const res = getGoodsMsg(id);
      // res
      //   .then((data) => {
      //     this.msgList = data
      //   })
      //   .catch((e) => {
      //     alert(e);
      //   })
    },

    addToCart() {
      if (!localStorage.getItem('id')) {
        this.$notify.error({
          title: '错误',
          message: '您还没有登陆，请先登陆后再试'
        });
        return;
      }
      axios({
        url:'http://localhost:8080/shoppingcarft/addToCarft',
        method:'POST',
        data:{
          id:null,
          userid:localStorage.getItem('id'),
          itemname:this.goodsName,
          price:this.goodsPrice,
          num:this.num,
          amount: this.goodsPrice * this.num
        }
      }).then(result =>{
        if (result){
          this.$notify.success({
            title:'成功',
            message:'请在个人中心查看详情'
          })
        }else {
          this.$notify.error({
            title:'失败',
            message:'加入购物车失败，请联系管理员'
          })
        }
      })
    },

    buy() {
      if (!localStorage.getItem('id')) {
        this.$notify.error({
          title: '错误',
          message: '您还没有登陆，请先登陆后再试'
        });
        return;
      }
      axios({
        url:'http://localhost:8080/order/sendorder',
        method:'POST',
        data:{
          ordertime: moment().format('YYYY-MM-DD HH:mm:ss'),
          orderuserid: localStorage.getItem('id'),
          orderusername: localStorage.getItem('username'),
          pretime: moment().add(30,'m').format('YYYY-MM-DD HH:mm:ss'),
          price: this.goodsPrice,
          status:0
        }
      }).then(result =>{
        if (result){
          this.$notify.success({
            title:'下单成功！',
            message:'请在个人中心查看订单详情'
          })
        }else {
          this.$notify.error({
            title:'错误',
            message:'请联系管理员'
          })
        }
      })
    },

    getComment(goodsId) {
      axios({
        url:"http://localhost:8080/comment/getcommbyid",
        method:"GET",
        params:{
          id: goodsId
        }
      }).then(result =>{
        this.commList = result.data
        console.log(this.commList)
        for (let i = 0; i < this.commList.length; i++) {
          this.rating[i] = this.commList[i].rating
        }
        console.log(this.rating)
      })
    },

    getTypeGoodsList(typeId) {
      // const res = getGoodsList(typeId);
      // res.then((data) => {
      //   this.goodsList = data;
      // })
      //   .catch((e) => {
      //     alert(e);
      //   })
    },

  },
  mounted() {
    this.getGoodsInfo(this.id);
    this.getGoodsMsg(this.id);
  },
  beforeMount() {
    this.getComment(this.id);
  },

  watch: {
    $route(to, from) {
      this.getGoodsInfo(to.params.id);
      // this.getGoodsMsg(to.params.id);
      this.getComment(to.params.id);
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.GoodsDetail {
  .content {
    width: 100%;
    padding-top: 20px;

    .goodsInfo {
      width: 100%;
      overflow: hidden;

      .infoLeft {
        display: inline-block;
        width: 400px;
        height: 400px;
        float: left;
      }

      .infoRight {
        display: inline-block;
        float: right;
        width: 700px;

        .infoBox {
          margin-bottom: 30px;

          .name {
            font-size: 20px;
          }

          p {
            color: @fontDefaultColor;
            font-size: 15px;
          }

          .price {
            font-size: 35px;
            color: @falseColor;
          }

          span {
            color: @fontDefaultColor;
            font-size: 13px;
            display: inline-block;
            width: 50px;
          }

          .tips {
            width: auto;
            margin: 0 20px 0 5px;
          }

          .NumberInput {
            display: inline-block;
            vertical-align: middle;
          }
        }

        button {
          background-color: #b4a078;
          width: 170px;
          height: 50px;
          color: white;
          border: none;
          font-size: 19px;
          margin-right: 20px;
          margin-top: 10px;

          &:hover {
            opacity: 0.8;
          }
        }

        .buyBtn {
          border: 1px solid #b4a078;
          color: #b4a078;
          background-color: #f5f3ef;
        }
      }
    }

    .msgBox {
      margin: 50px 0;
      border: 1px solid @borderColor;
      padding-top: 0;

      .tagList {
        width: 100%;
        height: 40px;
        border-bottom: 1px solid @borderColor;
        background-color: #f5f5f5;

        li {
          width: 170px;
          height: 42px;
          position: relative;
          top: -2px;
          display: inline-block;
          text-align: center;
          line-height: 40px;
          font-size: 13px;
          cursor: pointer;

          &:hover {
            color: @thirdColor;
          }
        }

        .selected {
          background-color: white;
          border-top: 4px solid @thirdColor;
        }
      }

      .commentBody {
        padding: 20px;
        min-height: 100px;

        .rateBox {
          margin-bottom: 10px;

          span {
            color: @fontDefaultColor;
            display: inline-block;
            margin-right: 10px;
          }

          .rate {
            color: @falseColor;
            font-weight: 600;
            font-size: 30px;
          }
        }

        .commentList {
          width: 100%;

          li {
            width: 100%;
            display: block;
            margin: 0 auto;
            border-bottom: 1px solid @borderColor;
            padding: 20px 0;

            .userInfo {
              width: 80px;
              display: inline-block;
              text-align: center;

              img {
                margin: auto;
                display: block;
                width: 50px;
                height: 50px;
                border-radius: 50%;
                margin-bottom: 6px;
              }

              span {
                font-size: 13px;
                color: @fontDefaultColor;
              }
            }

            .commentInfo {
              display: inline-block;
              vertical-align: top;

              .starList {
                i {
                  color: #f9bd4f;
                }
              }

              .specName, .time {
                color: @fontDefaultColor;
                font-size: 13px;
                margin-top: 10px;
              }

              .comment {
                margin-top: 10px;
              }
            }
          }
        }

        .noComment {
          width: 100%;
          text-align: center;
          color: @thirdColor;
          padding-top: 30px;
        }
      }

      .msgBody {
        padding: 20px;
        min-height: 300px;

        .inputBox {
          margin-top: 20px;

          textarea {
            width: 88%;
            height: 50px;
            padding: 5px;
            border: 2px solid @borderColor;
            display: inline-block;
            overflow: hidden;
          }

          button, .banAsk {
            float: right;
            width: 10%;
            height: 50px;
            position: relative;
            display: inline-block;
            overflow: hidden;
            background-color: white;
            border: 2px solid @fontShallowColor;
            color: @fontDefaultColor;
          }

          .banAsk {
            background-color: @fontShallowColor;
            text-align: center;
            font-size: 12px;
            line-height: 50px;
            color: white;
            user-select: none;
          }
        }

        .msgList {
          margin-top: 20px;

          li {
            border-bottom: 1px solid @borderColor;
            padding: 10px 0;

            .ask, .answer {
              margin: 8px 0;
              width: 100%;

              .note {
                display: inline-block;
                color: white;
                text-align: center;
                width: 20px;
                height: 20px;
                border-radius: 50%;
                font-size: 10px;
                line-height: 20px;
              }

              .text {
                font-size: 14px;
              }

              .tipsInfo {
                float: right;
                font-size: 14px;
                color: @fontDefaultColor;
              }
            }

            .ask {
              .note {
                background-color: @falseColor;
              }
            }

            .answer {
              .note {
                background-color: @mainColor;
              }
            }
          }
        }
      }
    }

    .typeGoods {
      margin: 50px 0;
      border: 1px solid @borderColor;
      padding-top: 0;

      .title {
        text-align: center;
        width: 100%;
        height: 40px;
        line-height: 40px;
        background-color: #f5f5f5;
        font-weight: 600;
        border-bottom: 1px solid @borderColor;
      }

      .list {
        .GoodsItem {
          display: block;
          border-bottom: 1px dotted @borderColor;
          margin: 0 auto;
        }
      }
    }
  }

  .ban {
    user-select: none;
    cursor: not-allowed;
  }
}
</style>
