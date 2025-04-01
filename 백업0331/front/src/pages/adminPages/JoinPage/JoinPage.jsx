/**@jsxImportSource @emotion/react */
import { Link, useNavigate } from 'react-router-dom';
import * as s from './style';
import React, { useState } from 'react';
import { useJoinMutation } from '../../../mutations/authMutation';
import { Input } from '@mui/material';
import ValidInput from '../../../components/ValidInput/ValidInput';
import { SiGoogle, SiKakao, SiNaver } from 'react-icons/si';

function JoinPage(props) {
    const joinMutation = useJoinMutation();
    const navigate = useNavigate();
    const [ inputValue, setInputValue ] = useState({
        adminName: "",
        adminPassword: "",
        passwordCheck: "",
        email: "",
        tradeName: "",
    });

    const [ inputValidError, setInputValidError ] = useState({
        adminName: false,
        email: false,
        tradeName: false,
        adminPassword: false,
        passwordCheck: false,
    });

    const handleInputOnChange = (e) => {
        setInputValue(prev => ({
            ...prev,
            [e.target.name]: e.target.value,
        }));
        console.log(inputValue)
    }

    const handlePasswordOnFocus = () => {
        setInputValue(prev => ({
            ...prev,
            adminPassword: "",
            passwordCheck: "",
        }));
    }


    const handleJoinOnClick = async () => {
        // 오류 상태 초기화
        setInputValidError({
            adminName: false,
            email: false,
            tradeName: false,
            adminPassword: false,
            passwordCheck: false,
        });

        // 필수 정보가 입력되었는지 확인
        const isEmpty = Object.values(inputValue).map(value => !!value).includes(false);
        const isValid = Object.values(inputValidError).includes(true);
    
        if (isEmpty || isValid) {
            alert("가입 정보를 다시 확인해주세요.");
            return;
        }
    
        // 비밀번호 불일치 처리
        if (inputValue.adminPassword !== inputValue.passwordCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            setInputValidError(prev => ({
                ...prev,
                adminPassword: true,
                passwordCheck: true
            }));
            return;  // 비밀번호 불일치 시 오류 처리 후 종료
        }
    
        try {
            // 회원가입 요청
            const response = await joinMutation.mutateAsync({
                adminName: inputValue.adminName, 
                adminPassword: inputValue.adminPassword,
                passwordCheck: inputValue.passwordCheck,
                email: inputValue.email,
                tradeName: inputValue.tradeName,
            });
    
            // 가입 성공 시, 로그인 페이지로 리디렉션
            alert("가입해 주셔서 감사합니다.");
            navigate("/admin/login");  // 로그인 페이지로 리디렉션
    
        } catch (error) {
            console.error("회원가입 오류:", error);
    
            // 오류가 발생한 경우, 서버의 상태 코드와 메시지를 확인하여 사용자에게 안내
            if (error.response?.status === 400) {
                // 예시로 필드 오류 표시
                setInputValidError(prev => ({
                    ...prev,
                    adminName: true,
                }));
                alert("입력한 정보가 유효하지 않습니다. 다시 확인해주세요.");
            } else {
                alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        }
    };
    
    
    
    
    

    const handleOAuth2LoginOnClick = (provider) => {
        window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`;
    }

    return (
        <div css={s.container}>
            <div css={s.logoContainer}>
                    <img src="https://blog.kakaocdn.net/dn/w1UK3/btqwTx0mNVX/ki6E4Mva5YavwrOFJQkCP1/img.jpg" alt="" />
                </div>
                <div css={s.test1}>
                    <div css={s.formContainer}>
                        <div>
                            <h1 css={s.title}>McDonald Admin</h1>
                            <ValidInput
                                type={"text"}
                                name={"adminName"}
                                placeholder={"사용자이름"}
                                value={inputValue.adminName}
                                onChange={handleInputOnChange}
                                inputValidError={inputValidError}
                                />
                            <ValidInput
                                type={"password"}
                                name={"adminPassword"}
                                placeholder={"비밀번호"}
                                value={inputValue.adminPassword}
                                onChange={handleInputOnChange}
                                onFocus={handlePasswordOnFocus}
                                inputValidError={inputValidError}
                                />
                            <ValidInput
                                type={"password"}
                                name={"passwordCheck"}
                                placeholder={"비밀번호 확인"}
                                value={inputValue.passwordCheck}
                                onChange={handleInputOnChange}
                                inputValidError={inputValidError}
                                />
                                <ValidInput
                                type={"text"}
                                name={"email"}
                                placeholder={"이메일"}
                                value={inputValue.email}
                                onChange={handleInputOnChange}
                                inputValidError={inputValidError}
                                />
                            <ValidInput
                                type={"text"}
                                name={"tradeName"}
                                placeholder={"상호명"}
                                value={inputValue.tradeName}
                                onChange={handleInputOnChange}
                                inputValidError={inputValidError}
                                />
                        </div>
                    </div>

                    <div css={s.rightContainer}>
                        <h3 css={s.socialLoginTitle}>간편 회원가입</h3>
                        <div css={s.socialLoginBox}>
                            <img onClick={() => handleOAuth2LoginOnClick("google")} src="https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/5rH/image/aFrEyVpANu07FvoBZQbIB4aF_uc"  alt="Google" />
                            <img onClick={() => handleOAuth2LoginOnClick("naver")}src="https://i.namu.wiki/i/p_1IEyQ8rYenO9YgAFp_LHIAW46kn6DXT0VKmZ_jKNijvYth9DieYZuJX_E_H_4GkCER_sVKhMqSyQYoW94JKA.svg" alt="Naver" />
                            <div css={s.buttonContainer}>
                                <Link css={s.button} to={"/admin/login"}> 로그인</Link>               
                                <button css={s.button} onClick={handleJoinOnClick}>
                                    가입하기
                                </button>
                            </div>
                            <img src="https://upload.wikimedia.org/wikipedia/commons/e/e3/KakaoTalk_logo.svg" alt="Kakao" />
                        </div>
                    </div>
                </div>
                
        </div>
    );
}

export default JoinPage;