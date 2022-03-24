package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    // TODO add user Id
    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredential(Integer credentialId);

    // TODO insert user Id
    @Insert("INSERT INTO CREDENTIALS " +
            "(url, username, key, password, userid) " +
            "VALUES" +
            "(#{url}, #{userName}), #{key}, #{password}, #{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS " +
            "SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId} " +
            "WHERE credentialId = #{credentialId}")
    int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int delete(Integer credentialId);
}
