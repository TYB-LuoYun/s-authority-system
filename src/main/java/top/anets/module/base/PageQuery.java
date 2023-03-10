package top.anets.module.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

/**
 * @Author ftm
 * @Date 2023-01-30 10:25:17
 * @Description Query分页构造器
 */

@Data
public class PageQuery {
    Integer size = Integer.MAX_VALUE;
    Integer current = 1;
    public IPage Page(){
        //防止重写
        if(current==null){
            current = 1;
        }
        if(size==null){
            size = Integer.MAX_VALUE;
        }
        IPage page = new Page<>(current, size);
        return page;
    }
}