package com.uvideo.miaosha.vo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_goods")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,property = "@id")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private float goodsPrice;
    private int goodsStock;
    private Date createTime;
    private Date lastModifiedTime;

    @OneToOne(mappedBy = "goods")
    private SeckillGoods seckillGoods;


}
