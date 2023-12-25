import 'babel-polyfill';
import Vue from 'vue';
import App from './App';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/admin';
import store from './store';

import './assets/css/common.less';
import './assets/font/iconfont.css';

Vue.config.productionTip = false;
Vue.use(ElementUI)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App },
  render: h => h(App)
});
