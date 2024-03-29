package board.study.service;

import board.study.mapper.MemberMapper;
import board.study.memberDTO.MemberDTO;
import board.study.springsecurity.AUser;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j
public class UserDetailsServiceImpl implements UserDetailsService { // 사용자 인증 및 권한 부여와 관련된 역할을 수행하는 데 중점    MemberDTO는 정보를 저장하거나 전송하기 위해 사용
    @Autowired                                                                //사용자의 인증 및 권한 부여를 위해 사용자 정보를 가져오는 로직
    MemberMapper memberMapper;//주입 잘됬음

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String loginID) throws UsernameNotFoundException {

        log.warn(memberMapper.selectMember(loginID));

        log.warn("------------------"+loginID);

        MemberDTO memberDTO = memberMapper.selectMember(loginID);

        log.warn("++++++++++++++++++"+memberDTO);
            //                          참 :  거짓
            return memberDTO == null ? null : new AUser(memberDTO) ;
    }

    public MemberDTO selectMember(String loginID){
        return memberMapper.selectMember(loginID);
    }//유저아이디 입력하면 회원정보 찾아주는것
    
    public void registerMember(MemberDTO memberDTO){
        memberDTO.setUserpw(passwordEncoder.encode(memberDTO.getUserpw()));
        memberMapper.memberRegister(memberDTO);
        memberMapper.memberAuthRegister(memberDTO.getUserid(),"ROLE_MEMBER"); //일단 하나만 추후 추가가능
    }



}
