package z;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

/**
 * 最低优先级中，字母排序最低的优先级
 */
@Configuration
@Order(LOWEST_PRECEDENCE)
@AutoConfigureOrder(LOWEST_PRECEDENCE)
public class ZAutoConfiguration implements Ordered {

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
