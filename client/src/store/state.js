import {getLocalItem} from '../util/util';
const state = {
  //后台管理
  adminToken:getLocalItem('id')?getLocalItem('id'):null,
  adminName:getLocalItem('username')?getLocalItem('username'):'',

  //客户商城
  clientName:getLocalItem('username')?getLocalItem('username'):'',
  clientToken:getLocalItem('id')?getLocalItem('id'):null,
}

export default state;
