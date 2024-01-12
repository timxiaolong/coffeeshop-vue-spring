<template>
  <div class="ClientLogin" :style="{width:width+'px',height:height+'px'}">
    <div class="content">
      <h3>一见倾心咖啡厅</h3>
      <div class="tag">
        <span @click="setIndex(0)" :class="{selected:curIndex===0}">登录</span>
        <span @click="setIndex(1)" :class="{selected:curIndex===1}">注册</span>
      </div>
      <div class="formBox" v-show="curIndex===0">
        <input class="email" type="text" placeholder="邮箱"/>
        <input class="password" type="password" placeholder="密码"/>
        <button @click="login">登录</button>
      </div>
      <form class="signin-form" v-show="curIndex===1">
        <input class="signName" type="text" placeholder="请输入昵称"/>
        <input class="signEmail" type="text"  placeholder="请输入邮箱"/>
        <input class="signPwd" type="password" placeholder="请输入密码"/>
        <input class="signPhNum" type="text"  placeholder="请输入注册手机号"/>
        <button @click="signup">注册</button>
      </form>
<!--      <div class="formBox" v-show="curIndex===1">-->
<!--        <input ref="signName" type="text" placeholder="请输入昵称"/>-->
<!--        <input ref="signPwd" type="password" placeholder="请输入密码"/>-->
<!--        <input ref="signEmail" type="text" placeholder="请输入注册手机号"/>-->
<!--        <button @click="signup">注册</button>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script>
import {getClientSize} from '../../util/util';
import axios from "axios";

export default {
  name: 'ClientLogin',
  computed: {
    width() {
      return getClientSize().width;
    },
    height() {
      return getClientSize().height;
    },
  },
  data() {
    return {
      curIndex: 0,
      checked: false
    }
  },
  methods: {
    setIndex(index) {
      if (index === this.curIndex) {
        return;
      }
      this.curIndex = index;
    },
    login() {
      console.log("login")
      const email = document.querySelector('.email').value
      const password = document.querySelector('.password').value
      if (email === '' || password === ''){
        this.$message.error('请填写所有信息再尝试登陆')
        return
      }
        axios({
          url:'http://localhost:8080/user/login',
          method: 'GET',
          params: {
            email:email,
            password: password
          }
        }).then(result => {
          if (result.data.status === 200){
            localStorage.setItem('username',result.data.param.name)
            localStorage.setItem('role',result.data.param.phone)
            localStorage.setItem('id',result.data.param.id)
            this.$message.success("登录成功")
            setTimeout("location.href=\"#/mall/show/index\"",3000)
          }else {
            this.$message.error("用户名或密码错误")
          }
        }).catch(err => {
          console.dir(err)
        })
    },
    signup() {
      const name = document.querySelector('.signName').value
      const password = document.querySelector('.signPwd').value
      const phone =document.querySelector('.signPhNum').value
      const email =document.querySelector('.signEmail').value
      if(name === '' || password === '' || phone === '' || email ===''){
        this.$message.error('请填写所有信息再尝试注册')
        return
      }
      axios({
        url:"http://localhost:8080/user/signin",
        method:"POST",
        data:{
          name,
          password,
          phone,
          email
        }
      }).then(result =>{
        if (result.data.status === 200){
          this.$message.success(result.data.message)
          setTimeout(()=>{
            this.curIndex = 0
            document.querySelector('.signName').value = ''
            document.querySelector('.signPwd').value = ''
            document.querySelector('.signPhNum').value = ''
            document.querySelector('.signEmail').value = ''
          },3000)
        }else {
          this.$message.error(result.data.message)
        }
      })

    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.ClientLogin {
  background-color: @bgColor;
  position: relative;

  .content {
    width: 300px;
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -260px;
    margin-left: -150px;
    text-align: center;
    overflow: hidden;

    h3 {
      color: @secondColor;
      font-size: 50px;
    }

    .tag {
      margin-top: 20px;
      color: @fontDefaultColor;
      margin-bottom: 20px;

      span {
        display: inline-block;
        width: 50px;
        text-align: center;
        margin: 0 10px;
        padding: 10px 0;
        cursor: pointer;
      }

      .selected {
        border-bottom: 2px solid @secondColor;
        color: @secondColor
      }
    }

    input {
      border-radius: 0;
      box-shadow: none;
      background: #fff;
      padding: 14px;
      width: 80%;
      border: 1px solid @borderColor;
      margin-bottom: 10px;
    }

    button {
      width: 90%;
      background: @secondColor;
      box-shadow: none;
      border: 0;
      border-radius: 3px;
      line-height: 41px;
      color: #fff;
      cursor: pointer;
      margin-top: 20px;
    }
  }
}
</style>
