/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.terra.ems.common.exception;

import com.terra.ems.common.constant.ErrorCodes;
import com.terra.ems.common.domain.Result;
import org.apache.commons.lang3.StringUtils;

/**
 * 业务异常基类
 *
 * @author dengxueping
 * @since 2026-01-11
 */
public class TerraException extends RuntimeException {

    private final ErrorCodes errorCode;

    public TerraException(ErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public TerraException(String message) {
        super(message);
        this.errorCode = ErrorCodes.INTERNAL_SERVER_ERROR;
    }

    public TerraException(ErrorCodes errorCode, String message) {
        super(StringUtils.defaultIfBlank(message, errorCode.getMessage()));
        this.errorCode = errorCode;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public Result<String> getResult() {
        return Result.failure(super.getMessage(), String.valueOf(errorCode.getSequence()), errorCode.getStatus(),
                (String) null);
    }
}
