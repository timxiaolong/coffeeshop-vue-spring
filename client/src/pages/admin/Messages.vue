<template>
  <div class="Messages">
    <header class="clear">
  		<span>留言管理</span>
  	</header>
  	<Tag :tagList="tags"/>
  	<div class="content">
  		<ul class="msgList" >
  			<li v-for="(item,index) in commList" :key="'noReply'+item.id" class="clear">
<!--  				<img :src="item.user.headimg" alt="" />-->
  				<div class="info">
  					<span class="name">{{item.userid}}</span>
  					<div class="goods ellipsis">商品：{{item.itemname}}</div>
  					<p>{{item.commentcontent}}</p>
  				</div>
  				<div class="operate">
  					<div>{{item.commenttime}}</div>
  					<button @click="dele(item.id)">删除</button>
  				</div>
  			</li>
  		</ul>
  	</div>
  </div>
</template>

<script>
import {getNoReplyMsg,getRepliedMsg,reply} from '../../api/admin';
import Tag from '../../components/Tag';
import Popup from '../../components/Popup';
import axios from "axios";

export default {
  name: 'Messages',
  components:{
  	Tag,
  	Popup
  },
  computed:{
  },
  data(){
  	return{
  		tags:['评价'],
  		noReplyMsgList:[],
  		repliedMsgList:[],
      commList:[],
  		curIndex:0,
  		curMsgId:0,
  		popupShow:false
  	}
  },
  methods:{
  	// changeTag(index){
  	// 	this.curIndex = index;
  	// 	if(index===1){
  	// 		this.getRepliedMsg();
  	// 	}else{
  	// 		this.getNoReplyMsg();
  	// 	}
  	// },
  	getNoReplyMsg(){
  		const res = getNoReplyMsg();
  		res.then((msgs)=>{
  			this.noReplyMsgList = msgs;
  		})
  		.catch((e)=>{
  			alert(e);
  		})
  	},
  	getMsg(){
      axios({
        url:'http://localhost:8080/comment/getallcomm',
        method:'GET'
      }).then(result =>{
        console.log(result.data)
        this.commList = result.data
      })
      console.log(this.commList)

      // for (let i = 0; i <= this.commList.length; i++) {
      //   console.log(this.commList)
      //
      //   //获取商品名称
      //   axios({
      //     url:'http://localhost:8080/menu/getmenubyid',
      //     method:'GET',
      //     params:{
      //       id: this.commList[i].id
      //     }
      //   }).then(result =>{
      //     console.log(result)
      //     this.commList[i].itemname = result.data
      //   })
      //   //获取用户名称
      //   axios({
      //     url:'http://localhost:8080/user/getuserinfobyid',
      //     method:'GET',
      //     params:{
      //       id:this.commList[i].userid
      //     }
      //   }).then(result =>{
      //     console.log(result)
      //     this.commList[i].userid = result.data.username
      //   })
      // }
  	},
    dele(id){
      console.log(id)
      axios({
        url:'http://localhost:8080/comment/deleById',
        method:'DELETE',
        params:{
          id:id
        }
      }).then(result =>{
        if (result){
          this.$notify.success({
            title:'成功！',
            message:'成功删除一条信息'
          })
          this.getMsg()
        }else {
          this.$notify.error({
            title:'错误',
            message:'删除失败'
          })
        }
      })
    },
  	reply(id){
  		this.popupShow = true;
  		this.curMsgId = id;
  		this.$refs.replyText.value = '';
  	},
  	closePopup(){
  		this.popupShow = false;
  	},
  	replyConfirm(){
  		const val = this.$refs.replyText.value;
  		const res = reply({
  			id:this.curMsgId,
  			content:val
  		});
  		res
  		.then(()=>{
  			alert('回复成功!');
  			this.noReplyMsgList.map((item,index)=>{
  				if(item.id===this.curMsgId){
  					this.noReplyMsgList.splice(index,1);
  				}
  			});
  			this.closePopup();
  		})
  		.catch((e)=>{
  			alert(e);
  		})
  	},
  },
  mounted(){
  	this.getMsg();
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.Messages{
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
		padding: 20px;
		.msgList{
			li{
				width: 100%;
				border: 1px solid @borderColor;
				padding: 10px;
				position: relative;
				margin-bottom: 5px;
				img{
					position: absolute;
					width: 48px;
					height: 48px;
					display: inline-block;
					margin-right: 20px;
					top: 50%;
					margin-top: -24px;
				}
				.info{
					display: inline-block;
					margin-left: 70px;
					max-width: 820px;
					font-size: 13px;
					.name{
						font-size: 15px;
					}
					.goods{
						color:@mainColor;
						font-size: 13px;
						margin-top: 10px;
					}
					p{
						margin-top: 10px;
						color:@fontDefaultColor;
					}
					.replyContent{
					}
				}
				.operate{
					display: inline-block;
					width: 150px;
					height: 60px;
					position: absolute;
					margin-top: -30px;
					top: 50%;
					right:10px;
					text-align: right;
					div{
						text-align: right;
						color:@fontDefaultColor;
						margin-bottom: 15px;
					}
					button{
						width: 50px;
						height: 25px;
						color:@secondColor;
						border: 1px solid @secondColor;
						background-color: white;
						border-radius: 5px;
					}
				}
			}
		}
	}
	.popupContent{
		width: 300px;
		padding: 8px;
		textarea{
			width: 100%;
			height: 100px;
			display: block;
			border: 1px solid @borderColor;
			padding: 3px;
		}
		button{
			display: block;
			width: 70px;
			height: 30px;
			margin:10px auto 5px;
			background-color:  #333333;
			color:white;
			border: none;
		}
	}
}
</style>
