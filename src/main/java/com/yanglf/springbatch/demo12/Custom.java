package com.yanglf.springbatch.demo12;

import lombok.Builder;
import lombok.Data;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
@Data
@Builder
public class Custom {
    private Long id;
    private String username;
    private String password;
    private Integer age;
}