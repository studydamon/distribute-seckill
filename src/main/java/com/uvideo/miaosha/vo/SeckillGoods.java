package com.uvideo.miaosha.vo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_seckill_goods")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,property = "@id")
public class SeckillGoods {
    // https://blog.csdn.net/pmdream/article/details/107412779
    // https://blog.csdn.net/peng_0129/article/details/99054861
    // SpringBean 循环依赖 -> https://blog.csdn.net/qq_36381855/article/details/79752689
    // https://www.jianshu.com/p/8bb67ca11831
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    private float goodsPrice;
    private int goodsStock;

    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date lastModifiedTime;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "goodsId",referencedColumnName = "id")
    private Goods goods;

}
