import {getLocalItem} from '../util/util';
const state = {
  //后台管理
  adminToken:getLocalItem('adminToken')?getLocalItem('adminToken'):null,
  adminName:getLocalItem('adminName')?getLocalItem('adminName'):'',

  //客户商城
  clientName:getLocalItem('username')?getLocalItem('username'):'',
  clientToken:getLocalItem('id')?getLocalItem('id'):null,
}

export default state;
