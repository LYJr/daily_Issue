package com.example.daily_issue.login.service;

import com.example.daily_issue.login.domain.Member;
import com.example.daily_issue.login.domain.MemberPrincipal;
import com.example.daily_issue.login.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WebApplicationContext applicationContext;

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
    public MemberService(){
        super();
    }

    @PostConstruct
    public void completeSetup(){
        memberRepository = applicationContext.getBean(MemberRepository.class);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       Optional<Member> originMember  = findByUserName(userName);

       if(!originMember.isPresent()) {
           throw new UsernameNotFoundException("Not User");
       }

       Member member = originMember.get();
       return new MemberPrincipal(member);
    }

    private Optional<Member> findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }

//    /**
//     * DuplicateKeyException을 사용할지 고민중
//     * @param memberDto
//     * @return
//     * @throws DuplicateKeyException
//     */
    public Member add(Member member)  {

        return memberRepository.save(member);
    }

//
//    public List<Member> findAll () {
//        return userRepository.findAll();
//    }

 //   public Optional<Member> findByUserId(String userId) {
//        return memberRepository.findByUserId(userId);
 //   }


//    public boolean login(MemberDto memberDto) {
//        Optional<Member> originUser = findByUserId(memberDto.getUserId());
//
//        if (originUser.isPresent()) {
//            Member user = originUser.get();
//            if(user.passwordConfirm(memberDto.getPassword())){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 아이디, 핸드폰 번호 확인 메서드
//     * @param memberDto
//     * @return
//     * @throws IllegalArgumentException
//     */
//    public Member userConfirm (MemberDto memberDto) {
//        return memberDto.toMember();
//    }
//
//    private String conversion (String input) {
//        return (input);
//    }
//
//   public Member createNew (Member member) {
//        member.encodePassword(passwordEncoder);
//        return add(member);
//   }
//
//   public String TestPassword(Member member) {
//        member.encodePassword(passwordEncoder);
//        return member.getPassword();
//   }


}
