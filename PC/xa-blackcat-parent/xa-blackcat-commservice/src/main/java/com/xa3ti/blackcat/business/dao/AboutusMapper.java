package com.xa3ti.blackcat.business.dao;

import com.xa3ti.blackcat.business.model.Aboutus;
import com.xa3ti.blackcat.business.model.AboutusExample;
import com.xa3ti.blackcat.business.model.AboutusWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AboutusMapper {
    int countByExample(AboutusExample example);

    int deleteByExample(AboutusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AboutusWithBLOBs record);

    int insertSelective(AboutusWithBLOBs record);

    List<AboutusWithBLOBs> selectByExampleWithBLOBs(AboutusExample example);

    List<Aboutus> selectByExample(AboutusExample example);

    AboutusWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AboutusWithBLOBs record, @Param("example") AboutusExample example);

    int updateByExampleWithBLOBs(@Param("record") AboutusWithBLOBs record, @Param("example") AboutusExample example);

    int updateByExample(@Param("record") Aboutus record, @Param("example") AboutusExample example);

    int updateByPrimaryKeySelective(AboutusWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AboutusWithBLOBs record);

    int updateByPrimaryKey(Aboutus record);
}