package com.gfd.eshop.network.core;


public interface ApiPath {

    String COLLECT_DELETE = "user/collect/delete";
    String COLLECT_LIST = "user/collect/list";
    String COLLECT_CREATE = "user/collect/create";
    String ORDER_CANCEL = "order/cancel";
    String ORDER_LIST = "order/list";
    String ORDER_DONE = "flow/done";
    String ORDER_PREVIEW = "flow/checkOrder";
    String ADDRESS_INFO = "address/info";
    String ADDRESS_UPDATE = "address/update";
    String ADDRESS_DELETE = "address/delete";
    String ADDRESS_DEFAULT = "address/setDefault";
    String ADDRESS_ADD = "address/add";
    String ADDRESS_LIST = "address/list";
    String CART_CREATE = "cart/create";
    String CART_DELETE = "cart/delete";
    String CART_LIST = "cart/list";
    String CART_UPDATE = "cart/update";
    String CATEGORY = "category";
    String GOODS = "goods";
    String HOME_DATA = "home/data";
    String HOME_CATEGORY = "home/category";
    String REGION = "region";
    String SEARCH = "search";
    String USER_SIGNIN = "user/signIn";
    String USER_SIGNUP = "user/signup";
    String USER_INFO = "user/info";
    String HELP = "https://www.jianshu.com/u/1fa8a6a319f4";

}
