<template>
  <div class="EditGoods">
    <header class="clear">
      <span>商品编辑</span>
    </header>
        <div class="content">
    <el-form ref="form" :model="form" label-width="80px" :rules = "rules" class="my-form">
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="form.name" name="name"></el-input>
      </el-form-item>
      <el-form-item label="品类">
        <el-radio-group v-model="form.typeid"
                        v-for="(item,index) in types" :key="item.id" :initVal="String(initTypeId)" radioName="type" :radioVal="String(item.id)">
          <el-radio :label=index+1 v-model="form.type" name="type">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="图片地址">
        <el-input v-model="form.picture" name="picture"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model="form.price" name="price"></el-input>
      </el-form-item>
      <el-form-item label="商品描述">
        <el-input type="textarea" v-model="form.describtion" name="describtion"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveChange">立即创建</el-button>
        <el-button @click="back">取消</el-button>
      </el-form-item>
    </el-form>
        </div>

    <Popup title="添加规格" @popupClose="closePopup" v-show="popupShow">
      <div class="popupContent" slot="popupContent">
        <input type="text" ref="specNameInput" placeholder="请输入规格名称" />
        <input type="text" ref="stockNumInput" placeholder="请输入库存量" />
        <input type="text" ref="priceInput" placeholder="请输入单价" />
        <button @click="addConfirm">确认</button>
      </div>
    </Popup>

  </div>

</template>
<script>
import Popup from '../../components/Popup';
import TextInput from '../../components/TextInput';
import Radio from '../../components/Radio';
import {getTypes,getGoodsInfo,addGoods,addSpec,deleteSpec,updateGoods} from '../../api/admin';
import axios from "axios";
export default {
  name: 'EditGoods',
  components:{
    Popup,
    Radio,
    TextInput
  },
  data(){
    return{
      popupShow:false,
      id:this.$route.params.id,
      goodsName:'',
      types:[],
      initTypeId:'',
      temTypeId:'',
      goodsImg:'',
      desc:'',
      specList:[
        {
          specName:'默认',
          stockNum:0,
          unitPrice:0,
        }
      ],
      form: {
        name: '',
        price: 0,
        picture:'',
        typeid:0,
        describtion: ''
      },
      rules:{
        name:[{required :true, message:'请输入商品名称', trigger:'blur'}],
        price: [{required: true, message:'请输入价格', trigger:'blur'}]
      }
    }
  },
  methods:{
    getTypes(){
      axios({
        url:'http://localhost:8080/type/getalltype',
        method:'GET'
      }).then(result =>{
        this.types = result.data
      })
    },
    back(){
      this.$router.go(-1);
    },
    saveChange(){
      // this.$notify.error({
      //   title: '错误',
      //   message: '有选项未填写，请重试'
      // });
      // const form = document.querySelector('.my-form')
      // const data = serialize(form,{hash:true,empty:true})
      console.log(this.form)
      axios({
        url:'http://localhost:8080/menu/changeGoods',
        method:'POST',
        data:this.form
      }).then(result =>{
        if (result){
          if(this.id==='new') {
            this.$notify.success({
              title:'成功',
              message:'商品成功添加！'
            })
          }else {
            this.$notify.success({
              title:'成功',
              message:'商品成功修改！'
            })
          }
        }else {
          this.$notify.error({
            title:'错误',
            message:'商品操作失败！'
          })
        }

      })
    },
    closePopup(){
      this.popupShow = false;
    },
    addConfirm(){
      if(this.id==='new'){
        const name = this.$refs.specNameInput.value;
        const stock = this.$refs.stockNumInput.value;
        const price = this.$refs.priceInput.value;
        this.specList.push({
          specName:name,
          stockNum:stock,
          unitPrice:price,
        });
        this.closePopup();
      }else{
        const res = addSpec({
          goodsId:this.id,
          specName:this.$refs.specNameInput.value,
          stockNum:this.$refs.stockNumInput.value,
          unitPrice:this.$refs.priceInput.value,
        });
        res.then((spec)=>{
          this.specList.push(spec);
          this.closePopup();
        })
        .catch((e)=>{
          alert(e);
        })
      }
    },
    showPopup(){
      this.popupShow = true;
    },
    deleteSpec(specname){
      if(this.id==='new'){
        this.specList.map((item,index)=>{
          if(item.specName===specname){
            this.specList.splice(index,1);
          }
        })
      }else{
        const res = deleteSpec({
          goodsId:this.id,
          specName:specname
        });
        res
        .then(()=>{
          this.specList.map((item,index)=>{
            if(item.specName===specname){
              this.specList.splice(index,1);
            }
          })
        })
        .catch((e)=>{
          alert(e);
        })
      }
    }
  },
  mounted(){
    this.getTypes();
    //新建商品
    if(this.id==='new'){

    }else{
      const res = getGoodsInfo(this.id);
      res
      .then((data)=>{
        this.temTypeId = data.goods.typeId;
        this.initTypeId = data.goods.typeId;
        this.goodsName = data.goods.name;
        this.goodsImg = data.goods.img;
        this.desc = data.goods.desc;
        this.specList = data.specs;
      })
      .catch((e)=>{
        alert(e);
      })
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.EditGoods{
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
      padding: 10px;
      textarea{
        width: 80%;
        height: 300px;
        border: 1px solid @borderColor;
      }
      .inputBox{
        margin-bottom: 30px;
        span{
          width: 90px;
          display: inline-block;
          color:@fontDefaultColor;
          font-weight: 600;
          vertical-align: middle;
        }
        .verTop{
          vertical-align: top;
        }
        .Radio /deep/ input:checked+.tipsBox{
          &:after{
            background-color:#337da4;
          }
        }
        .tips{
          font-weight: normal;
          width: auto;
          font-size: 13px;
          position: relative;
          left: 3px;
        }
        .val{
          width: auto;
          font-weight: 500;
          color:@fontDeepColor;
        }
        ul{
          display: inline-block;
          width: 500px;
          li{
            width: 100%;
            height: 40px;
            span{
              width: auto;
              font-size: 13px;
              font-weight: 500;
            }
            input{
              margin-right: 10px;
            }
            button{
              width: 50px;
              height: 25px;
              color:#d7514a;
              border:none;
              background-color: white;
              border-radius: 5px;
            }
          }
          .addSpec{
            text-align: center;
            line-height: 30px;
            background-color: #337da4;
            color:white;
            border: none;
            width: 100px;
            height: 30px;
            border-radius: 5px;
            font-size: 13px;
            cursor: pointer;
            margin:10px auto;
          }
        }
        .long{
          width: 900px;
        }
        .numInput{
          width: 30px;
          text-align: center;
          border: none;
          border-bottom: 2px solid @mainColor;
        }
      }
      .btnBox{
        margin: auto;
        display: block;
        width: 250px;
      }
      .confirmBtn{
        display: inline-block;
        margin-right: 30px;
        background-color: #337da4;
        color:white;
        border: none;
        width: 100px;
        height: 30px;
        border-radius: 5px;
        cursor: pointer;
      }
      .normalBtn{
        display: inline-block;
        background-color: grey;
        color:white;
        border: none;
        width: 100px;
        height: 30px;
        border-radius: 5px;
        cursor: pointer;
      }
  }
  .popupContent{
    padding: 10px;
    input{
      display: block;
      width: 400px;
      margin-bottom: 10px;
      height: 30px;
    }
    button{
        display: block;
        margin:auto;
        background-color: #333333;
        color:white;
        border: none;
        width: 100px;
        height: 30px;
        border-radius: 5px;
        cursor: pointer;
    }
  }
}
</style>
