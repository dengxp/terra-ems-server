package com.terra.ems.system.mapper;

import com.terra.ems.system.dto.UserDTO;
import com.terra.ems.system.entity.SysUser;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-21T14:00:11+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(SysUser entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( entity.getId() );
        userDTO.username( entity.getUsername() );
        userDTO.nickname( entity.getNickname() );
        userDTO.email( entity.getEmail() );
        userDTO.phone( entity.getPhone() );
        userDTO.avatar( entity.getAvatar() );
        userDTO.status( entity.getStatus() );
        userDTO.lastLoginAt( entity.getLastLoginAt() );
        userDTO.createdAt( entity.getCreatedAt() );
        userDTO.updatedAt( entity.getUpdatedAt() );

        userDTO.roleIds( entity.getRoles() != null ? entity.getRoles().stream().map(r -> r.getId()).collect(java.util.stream.Collectors.toList()) : null );

        return userDTO.build();
    }

    @Override
    public SysUser toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SysUser sysUser = new SysUser();

        sysUser.setCreatedAt( dto.getCreatedAt() );
        sysUser.setUpdatedAt( dto.getUpdatedAt() );
        sysUser.setId( dto.getId() );
        sysUser.setUsername( dto.getUsername() );
        sysUser.setNickname( dto.getNickname() );
        sysUser.setAvatar( dto.getAvatar() );
        sysUser.setEmail( dto.getEmail() );
        sysUser.setPhone( dto.getPhone() );
        sysUser.setStatus( dto.getStatus() );
        sysUser.setLastLoginAt( dto.getLastLoginAt() );

        return sysUser;
    }

    @Override
    public List<UserDTO> toDTOList(List<SysUser> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( entities.size() );
        for ( SysUser sysUser : entities ) {
            list.add( toDTO( sysUser ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDTO(UserDTO dto, SysUser entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
        if ( dto.getNickname() != null ) {
            entity.setNickname( dto.getNickname() );
        }
        if ( dto.getAvatar() != null ) {
            entity.setAvatar( dto.getAvatar() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getLastLoginAt() != null ) {
            entity.setLastLoginAt( dto.getLastLoginAt() );
        }
    }
}
