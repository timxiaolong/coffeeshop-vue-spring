<template>
  <div class="Mall">
    <header>
      <div class="container clear">
        <span class="title" @click="navTo('/mall')">一见倾心咖啡点单平台</span>
        <NoticeList :notices="notices"/>
        <div class="right" v-if="clientToken">
          <span class="name">欢迎您，{{clientName}}</span>
          <span @click="navTo('/mall/personal')">个人中心</span>
          <span @click="logout">退出登录</span>
        </div>
        <div class="right" v-else>
          <span @click="navTo('/login')">登录</span>
          <span @click="navTo('/login')">注册</span>
          <span><a href="http://localhost:8081/admin.html" style="color: white">我是管理员</a></span>

        </div>
      </div>
    </header>
    <div class="content" :style="{minHeight:clientHeight+'px'}">
      <div class="container">
        <router-view></router-view>
      </div>
    </div>
    <div class="bottomInfo">
      <div class="container">
        <div class="service footerItem">
          <p class="title">客户服务</p>
          <span><i class="iconfont icon-people_fill" />在线客服</span>
          <span><i class="iconfont icon-fabulous" />用户反馈</span>
        </div>
        <div class="intro footerItem">
          <p class="title">一见倾心</p>
          <p class="intro-p">一见倾心咖啡厅，作为独具特色的原创咖啡品牌，秉承着对咖啡的匠心与热情。我们不断探索世界各地的咖啡产地，精心挑选最优质的咖啡豆，从栽培、采摘到烘焙全程严格把控，努力呈现顶级品质的咖啡。我们坚持精致独特的调配工艺，希望为顾客奉上一杯杯香醇浓郁、口感纯正的完美咖啡体验。</p>
          <div>
            关注我们：
            <img src="http://yanxuan.nosdn.127.net/60068701f3a380911f237c26c91b39d0.png" alt=""/>
            <img src="http://yanxuan.nosdn.127.net/031e783d7ee645b6096980d0bf83079b.png" alt=""/>
            <img src="http://yanxuan.nosdn.127.net/0c8759a89cdbd7acf7f2921e6f0fad19.png" alt=""/>
          </div>
        </div>
        <div class="code footerItem">
          <p class="title">扫码下载APP</p>
          <img src="../../assets/img/code.png" alt=""/>
          <span>下载领8.8元代金券</span>
        </div>
      </div>
    </div>
    <footer>
      <div class="container">
        <ul class="footerTop">
          <li>
            <img src="//yanxuan.nosdn.127.net/e6021a6fcd3ba0af3a10243b7a2fda0d.png" alt="" />
            <span>无微不至的服务</span>
          </li>
          <li>
            <img src="//yanxuan.nosdn.127.net/e09c44e4369232c7dd2f6495450439f1.png" alt="" />
            <span>完美品质的保证</span>
          </li>
          <li>
            <img src="//yanxuan.nosdn.127.net/e72ed4de906bd7ff4fec8fa90f2c63f1.png" alt="" />
            <span>无忧的用心体验</span>
          </li>
        </ul>
        <div class="footerBottom">
          <ul>
            <li>关于我们</li>
            <li>帮助中心</li>
            <li>售后服务</li>
            <li>商务合作</li>
            <li>企业采购</li>
            <li>开放平台</li>
            <li>搜索推荐</li>
            <li>友情链接</li>
          </ul>
          <p>XX公司版权所有 © 1996-2023   食品经营许可证：XXXXXXXXXXXXXXXXX</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import { mapState,mapMutations } from 'vuex';
import NoticeList from '../../components/NoticeList';
import {getClientSize,backToTop} from '../../util/util';

export default {
  name: 'Mall',
  computed:{
    ...mapState([
      'clientToken',
      'clientName'
    ]),
  },
  components:{
    NoticeList
  },
  data () {
    return {
      notices:['今日特惠：拿铁买一送一',
        '【福利】领8.8新会员券'],
      clientHeight:getClientSize().height,
      shouldShowBT:false
    }
  },

  methods:{
    ...mapMutations({
      clientLogout: 'CLIENT_LOGOUT',
    }),
    navTo(route){
      this.$router.push(route)
    },
    logout(){
      // this.clientLogout();
      localStorage.clear()
      this.$router.go(0);

    },
    backToTop(){
      backToTop();
    },
    watchScrollTop(){
      let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      if(scrollTop>150){
        this.shouldShowBT = true;
      }else{
        this.shouldShowBT = false;
      }
    }
  },

  mounted(){
    document.addEventListener('scroll',this.watchScrollTop,false);
  },

  beforeDestroyed(){
    document.removeEventListener('scroll',this.watchScrollTop,false);
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.Mall{
  width: 100%;
  header{
    width: 100%;
    background-color: #333333;
    height:38px;
    color:@fontShallowColor;
    user-select:none;
    z-index: 10000;
    position: absolute;
    left: 0;
    top: 0;
    .container{
      position: relative;
      height:38px;
      .title{
        position: absolute;
        left: 0;
        display: inline-block;
        height: 26px;
        top: 50%;
        margin-top: -13px;
        line-height: 26px;
        font-size: 14px;
        cursor: pointer;
      }
      .NoticeListBox{
        position: absolute;
        left: 200px;
      }
      .right{
        position: absolute;
        right: 0;
        display: inline-block;
        height: 26px;
        top: 50%;
        margin-top: -13px;
        line-height: 26px;
        font-size: 14px;
        span{
          margin-left: 20px;
          cursor: pointer;
        }
        .name{
          cursor: default;
        }
      }
    }
  }
  .content{
    padding-top: 40px;
  }
  .fixedAd{
    position: fixed;
    right: 0;
    top: 108px;
    width: 72px;
    img{
      display: block;
      width: 100%;
      height: 154px;
    }
    .fixedList{
      margin-top: 2px;
      background-color: white;
      width: 100%;
      li{
        width: 100%;
        height: 80px;
        text-align: center;
        border-bottom: 1px solid @borderColor;
        cursor: pointer;
        padding-top: 12px;
        i{
          display: block;
          font-size: 30px;
          color:#666666;
        }
        span{
          display: block;
          font-size: 14px;
          color:#666666;
          margin-top: 4px;
        }
        &:last-child{
          border-bottom: none;
        }
        &:hover{
          i{
            color:@thirdColor;
          }
          span{
            color:@thirdColor;
          }
        }
      }
    }
  }
  .bottomInfo{
    width: 100%;
    height: 300px;
    border-top: 1px solid @borderColor;
    overflow: hidden;
    margin-top: 80px;
    .footerItem{
      width: 33%;
      height: 210px;
      position: relative;
      top: 45px;
      display: inline-block;
      text-align: center;
      vertical-align: middle;
      color:@fontDefaultColor;
      .title{
        color: @fontDeepColor;
        margin-bottom: 30px;
      }
    }
    .service{
      border-right: 1px solid @borderColor;
      span{
        display: inline-block;
        width: 80px;
        height: 100px;
        border:1px solid @borderColor;
        text-align: center;
        margin: 0 10px;
        font-size: 14px;
        cursor: pointer;
        &:hover{
          color:@thirdColor;
        }
        i{
          display: block;
          font-size: 30px;
          margin-top: 20px;
          margin-bottom: 10px;
        }
      }
    }
    .intro{
      border-right: 1px solid @borderColor;
      .intro-p{
        font-size: 13px;
        width: 300px;
        margin: 0 auto;
        text-align: left;
        color:@fontDeepColor;
        line-height: 1.8em;
      }
      div{
        text-align: left;
        font-size: 14px;
        margin-left: 47px;
        margin-top: 20px;
        img{
          margin: 0 6px;
          display: inline-block;
          vertical-align: middle;
        }
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
  }
  footer{
    width: 100%;
    height: 208px;
    background-color: #414141;
    color:white;
    overflow: hidden;
    .footerTop{
      padding: 36px 0;
      border-bottom: 1px solid #4f4f4f;
      width: 100%;
      li{
        display: inline-block;
        width: 33%;
        text-align: center;
        img{
          vertical-align: middle;
        }
        span{
          vertical-align: middle;
          font-size: 18px;
          margin-left: 10px;
        }
      }
    }
    .footerBottom{
      color:@fontDefaultColor;
      margin-top: 30px;
      font-size: 13px;
      text-align: center;
      ul{
        li{
          display: inline-block;
          cursor: pointer;
          padding: 0 6px;
          border-right: 2px solid @fontDefaultColor;
          &:last-child{
            border-right:none;
          }
        }
      }
      p{
        margin-top: 5px;
      }
    }
  }
}
</style>
