<template>
  <div class="MyData">
    <ul>
      <li>
        <span>昵称</span>
        <input type="text" v-model="nickname"/>
      </li>
      <li>
        <span>联系电话</span>
        <input type="text" v-model="phone"/>
      </li>
      <li>
        <span>密码</span>
        <button @click="dialogFormVisible = true">修改密码</button>
      </li>
      <li>
        <span>会员身份</span>
        <span>您还不是会员哦~</span>
        <button @click="dialogVipVisble = true">办理会员</button>
      </li>
    </ul>
    <button @click="updateUserData" class="saveBtn">保存</button>

    <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="原密码" :label-width="formLabelWidth">
          <el-input v-model="form.oldPwd"></el-input>
        </el-form-item>
        <el-form-item label="新密码" :label-width="formLabelWidth">
          <el-input v-model="form.newPwd"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" :label-width="formLabelWidth">
          <el-input v-model="form.confirmPwd"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatePwd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="提示"
      :visible.sync="dialogVipVisble"
      width="50%"
      center
    >
      <div style="align-content: center;text-align: center" class="code ">
        <span>会费10元/月，请按需办理，扫码支付，感谢对网站的支持！</span><br/><br/>
        <img class="payImg" src="../../assets/img/code2.png">
      </div>
      <span  slot="footer" class="dialog-footer">
                <el-button @click="dialogVipVisble = false">取 消</el-button>
                <el-button type="primary" @click="dialogVipVisble = false">我已付款，确认办理</el-button>
              </span>
    </el-dialog>
  </div>
</template>

<script>
import {mapState, mapMutations} from 'vuex';
import {getUserData, updateUserData, updatePwd} from '../../api/client';
import Popup from '../../components/Popup';
import axios from "axios";

export default {
  name: 'MyData',
  components: {
    Popup
  },
  computed: {
    ...mapState([
      'clientToken'
    ]),
  },
  data() {
    return {
      id: '',
      headimg: '',
      email: '',
      nickname: '',
      recipient: '',
      address: '',
      phone: '',
      popupShow: false,
      oldPwd: '',
      newPwd: '',
      confirmPwd: '',

      dialogTableVisible: false,
      dialogFormVisible: false,
      dialogVipVisble: false,
      form: {
        oldPwd:'',
        newPwd:'',
        confirmPwd:''
      },
      formLabelWidth: '120px'
    }
  },

  methods: {
    ...mapMutations({
      setClientName: 'SET_CLIENT_NAME',
    }),
    updateUserData() {
      // const res = updateUserData({
      //   id: this.id,
      //   nickname: this.nickname,
      //   recipient: this.recipient,
      //   address: this.address,
      //   phone: this.phone,
      // });
      // res
      //   .then(() => {
      //     alert('修改成功!');
      //     this.setClientName(this.nickname);
      //   })
      //   .catch((e) => {
      //     alert(e);
      //   })
      axios({
        url:'http://localhost:8080/user/changeinfo',
        method:'POST',
        data:{
          id:localStorage.getItem('id'),
          name:this.nickname,
          phone:this.phone
        }
      }).then(result =>{
        if (result.data.param){
          this.$message({
            message:result.data.message,
            type:"success"
          })
        }else {
          this.$message({
            message:result.data.message,
            type:"warning"
          })
        }
      })
    },
    closePopup() {
      this.popupShow = false;
    },
    showPopup() {
      this.popupShow = true;
    },
    updatePwd(done) {
      if (this.newPwd !== this.confirmPwd) {
        this.$message({
          message:'两次输入的密码不一致！',
          type:"warning"
        })
        return;
      }
      axios({
        url:'http://localhost:8080/user/changepwd',
        method:'POST',
        params:{
          id : localStorage.getItem('id'),
          oldPwd: this.form.oldPwd,
          confirmPwd: this.form.confirmPwd
        }
      }).then(result =>{
        console.log(result)
        this.dialogFormVisible = false
        this.form.confirmPwd = ''
        this.form.oldPwd = ''
        this.form.newPwd = ''
        if (result.data.status === 200){
          this.$message({
            message:result.data.message,
            type:"success"
          })
        }else {
          this.$message({
            message:result.data.message,
            type:"warning"
          })
        }
      })
    }
  },

  mounted() {
    axios({
      url: 'http://localhost:8080/user/getuserinfobyid',
      method:'GET',
      params:{
        id:localStorage.getItem('id')
      }
    }).then(result =>{
        this.nickname = result.data.name;
        this.phone = result.data.phone;
    })
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.MyData {
  ul {
    width: 100%;
    overflow: hidden;

    li {
      margin-bottom: 30px;

      &:first-child {
        height: 60px;
        line-height: 60px;

        span {
          position: relative;
          bottom: 18px;
        }
      }

      span {
        display: inline-block;
        width: 150px;
        height: 20px;
      }

      img {
        display: inline-block;
        width: 60px;
        height: 60px;
        border-radius: 50%;
      }

      p {
        display: inline-block;
      }

      input {
        width: 280px;
        border: none;
        text-align: center;
        border-bottom: 2px solid @thirdColor;
      }

      .long {
      }

      button {
        background-color: white;
        border: 1px solid @thirdColor;
        color: @thirdColor;
        width: 80px;
        height: 30px;
      }
    }
  }

  .saveBtn {
    background-color: @thirdColor;
    border: none;
    color: white;
    width: 110px;
    height: 35px;
    display: block;
    margin: 10px auto;
  }

  .payImg{
    width: 120px;
    height: 120px;
    display: inline-block;
  }

  .popupContent {
    padding: 20px;

    input {
      display: block;
      border: none;
      border-bottom: 1px solid #313541;
      margin-bottom: 16px;
      font-size: 13px;
      padding: 5px;
      width: 200px;
    }

    button {
      background-color: #333333;
      border: none;
      color: white;
      width: 80px;
      height: 32px;
      display: block;
      margin: 20px auto 5px;
    }
  }
}
</style>
