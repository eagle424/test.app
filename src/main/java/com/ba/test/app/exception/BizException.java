/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ba.test.app.exception;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * BizException : 비즈니스 서비스 구현체에서 발생시키는 Biz Exception 
 * 
 * @author 
 * @since 
 * @version 
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   
 *
 * </pre>
 */
public class BizException extends RCPException {

	private static final long serialVersionUID = 1L;

	/**
	 * BizException 생성자
	 */
	public BizException() {
		this("RCPException without message", new Object[0], null);
	}

	/**
	 * BizException 생성자
	 * @param defaultMessage 메세지 지정
	 */
	public BizException(String defaultMessage) {
		this(defaultMessage, new Object[0], null);
	}
	/**
	 * BizException 생성자
	 * @param defaultMessage 메세지 지정
	 * @param wrappedException 발생한 Exception 내포함
	 */
	public BizException(String defaultMessage, Exception wrappedException) {
		this(defaultMessage, new Object[0], wrappedException);
	}

	/**
	 * BizException 생성자
	 * @param defaultMessage 메세지 지정(변수지정)
	 * @param messageParameters 치환될 메세지 리스트
	 * @param wrappedException 발생한 Exception 내포함.
	 */
	@SuppressWarnings("null")
	public BizException(String defaultMessage, Object[] messageParameters, Exception wrappedException) {
		String userMessage = defaultMessage;
		if (messageParameters != null || messageParameters.length > 0) {
			userMessage = MessageFormat.format(defaultMessage, messageParameters);
		}
		this.message = userMessage;
		this.wrappedException = wrappedException;
	}

	public BizException(String messageKey, Locale locale, Exception wrappedException) {
		this(messageKey, null, null, locale, wrappedException);
	}

	public BizException(String messageKey, Object[] messageParameters, Locale locale,
	        Exception wrappedException) {
		this(messageKey, messageParameters, null, locale, wrappedException);
	}

	public BizException(String messageKey, Object[] messageParameters, String defaultMessage, Exception wrappedException) {
		this(messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
	}

	public BizException(String messageKey, Object[] messageParameters, String defaultMessage, Locale locale, Exception wrappedException) {
		this.messageKey = messageKey;
		this.messageParameters = messageParameters;
		this.wrappedException = wrappedException;
	}

}

