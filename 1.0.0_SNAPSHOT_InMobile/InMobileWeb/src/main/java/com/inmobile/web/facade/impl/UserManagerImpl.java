package com.inmobile.web.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inmobile.web.bean.RegisterUserDTO;
import com.inmobile.web.bean.ReturnService;
import com.inmobile.web.bean.canonical.user.UserRequest;
import com.inmobile.web.bean.canonical.user.UserResponse;
import com.inmobile.web.facade.UserManager;
import com.inmobile.web.util.CommonConstants;
import com.inmobile.web.util.ConvertClassFormat;
import com.inmobile.web.util.UtilMethods;

@Service
public class UserManagerImpl implements UserManager{

	public ReturnService registerUserInMobile(RegisterUserDTO beanUser) {
		ReturnService beanReturn=new ReturnService();
		RestTemplate restTemplate=new RestTemplate();
		UserRequest beanRequest=ConvertClassFormat.convertWebToServiceUser(beanUser);
		System.out.println("Valor Request : "+UtilMethods.fromObjectToString(beanRequest));
		UserResponse beanResponse=restTemplate.postForObject(CommonConstants.URLService.URL_REGISTER_USER, beanRequest, UserResponse.class);
		System.out.println("Valor Response : "+UtilMethods.fromObjectToString(beanResponse));
		if(CommonConstants.Response.RESPONSE_USER_SUCCESS.equals(beanResponse.getCodeResponse())){
			beanReturn.setReturnPage(CommonConstants.Page.REDIRECT_SENDEMAIL);
		}else if(CommonConstants.Response.RESPONSE_USER_EXIST.equals(beanResponse.getCodeResponse())||
				CommonConstants.Response.RESPONSE_ERROR.equals(beanResponse.getCodeResponse())){
			beanReturn.setReturnPage(CommonConstants.Page.REDIRECT_LOGIN_PAGE);
			beanReturn.setSpecificMessages(beanResponse.getMessagesResponse());
		}
		beanReturn.setMessages(beanResponse.getCodeResponse());
		return beanReturn;
	}

	public ReturnService validateUser(RegisterUserDTO beanUser) {
		ReturnService beanReturn=new ReturnService();
		RestTemplate restTemplate=new RestTemplate();
		UserRequest beanRequest=ConvertClassFormat.convertWebToServiceUser(beanUser);
		System.out.println("Valor Request : "+UtilMethods.fromObjectToString(beanRequest));
		System.out.println("URL : "+CommonConstants.URLService.URL_VALIDATION_USER);
		UserResponse beanResponse=restTemplate.postForObject(CommonConstants.URLService.URL_VALIDATION_USER, beanRequest, UserResponse.class);
		System.out.println("Valor Response : "+UtilMethods.fromObjectToString(beanResponse));
		if(CommonConstants.Response.RESPONSE_SUCCESS_VALIDATION.equals(beanResponse.getCodeResponse())){
			if(CommonConstants.Response.RESPONSE_IS_RESTAURANT.equals(beanResponse.getMessagesResponse())){
				beanReturn.setReturnPage(CommonConstants.Page.REDIRECT_RESTAURANT);
			}else if(CommonConstants.Response.RESPONSE_IS_PROVIDER.equals(beanResponse.getMessagesResponse())){
				beanReturn.setReturnPage(CommonConstants.Page.REDIRECT_PROVIDER);
			}else if(CommonConstants.Response.RESPONSE_ACCOUNT_PENDING_VALIDATION.equals(beanResponse.getMessagesResponse())) {
				beanReturn.setReturnPage(CommonConstants.Page.REDIRECT_ACCOUNT_PENDING);
			}
			beanReturn.setSpecificMessages(beanResponse.getDescription());
		}else if(CommonConstants.Response.RESPONSE_FAIL_VALIDATION.equals(beanResponse.getCodeResponse()) ||
				CommonConstants.Response.RESPONSE_NOT_EXITS_USER.equals(beanResponse.getCodeResponse())){
			beanReturn.setReturnPage(CommonConstants.Page.REDIRECT_LOGIN_PAGE);
		}else if(CommonConstants.Response.RESPONSE_ACCOUNT_INACTIVE.equals(beanResponse.getCodeResponse())){
			beanReturn.setReturnPage(CommonConstants.Page.REDIRECT_INACTIVE_ACCOUNT_PAGE);
		}
		beanReturn.setMessages(beanResponse.getCodeResponse());
		beanReturn.setIdUser(beanResponse.getIdUser());
		return beanReturn;
	}
	
	public ReturnService activeAccountUser(String idUser){
		ReturnService returnServiceBean=new ReturnService();
		RestTemplate restTemplate=new RestTemplate();
		UserRequest beanRequest=new UserRequest();
		beanRequest.setEncriptUser(idUser);
		System.out.println("Valor Request : "+UtilMethods.fromObjectToString(beanRequest));
		System.out.println("URL : "+CommonConstants.URLService.URL_ACTIVATE_USER);
		UserResponse beanResponse=restTemplate.postForObject(CommonConstants.URLService.URL_ACTIVATE_USER, beanRequest, UserResponse.class);
		System.out.println("Valor Response : "+UtilMethods.fromObjectToString(beanResponse));
		if(!CommonConstants.Response.RESPONSE_ERROR.equals(beanResponse.getCodeResponse())){
			returnServiceBean.setIdUser(beanResponse.getIdUser());
			returnServiceBean.setSpecificMessages(beanResponse.getDescription());
			if(CommonConstants.Response.RESPONSE_IS_PROVIDER.equals(beanResponse.getCodeResponse())){
				returnServiceBean.setReturnPage(CommonConstants.Page.REDIRECT_PROVIDER);
			}else if(CommonConstants.Response.RESPONSE_IS_RESTAURANT.equals(beanResponse.getCodeResponse())){
				returnServiceBean.setReturnPage(CommonConstants.Page.REDIRECT_RESTAURANT);
			}
		}else{
			returnServiceBean.setReturnPage(CommonConstants.Page.REDIRECT_ERROR);
		}
		returnServiceBean.setSpecificMessages(beanResponse.getDescription());
		return returnServiceBean;
	}

	
	
}