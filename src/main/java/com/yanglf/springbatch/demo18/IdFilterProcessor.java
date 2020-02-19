package com.yanglf.springbatch.demo18;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author yanglf
 * @sine 2020.02.18
 * @descriptipon
 * @see
 */
public class IdFilterProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {
        // 只处理id  为  偶数 的  用户  id 为 奇数的 对象过滤掉 不传给  writer
        if (user.getId() % 2 == 0) {
            return user;
        }
        return null;
    }
}
