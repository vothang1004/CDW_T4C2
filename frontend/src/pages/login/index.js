import React from "react";
import MainLayout from "../../components/layouts/MainLayout";
import RestrictedRoute from "../../components/route/RestrictedRoute";
import Image from "next/image";
import Link from "next/link";
import Head from "next/head";
import FacebookBtnImage from "../../assets/images/fb_btn.svg";
import GoogleBtnImage from "../../assets/images/gg_btn.svg";
import FormLogin from "./components/FormLogin";

function LoginPage() {
  return (
    <RestrictedRoute>
      <Head>
        <title>Đăng nhập</title>
      </Head>
      <MainLayout>
        <div className="container min-h-[calc(100vh-60px-40px-100px)] px-4 py-[45px] flex items-center justify-center">
          <div className="shadow-md p-[15px] rounded-md w-[410px]">
            <h5 className="text-center text-xl uppercase">Đăng nhập</h5>
            <FormLogin />
            
            <p className="py-5 text-center text-[12px]">hoặc đăng nhập qua</p>
            <div className="flex items-center justify-center gap-2">
              <Image
                className="cursor-pointer"
                width={130}
                height={26}
                src={FacebookBtnImage}
                alt="facebook button"
              />
              <Image
                className="cursor-pointer"
                width={130}
                height={26}
                src={GoogleBtnImage}
                alt="facebook button"
              />
            </div>
          </div>
        </div>
      </MainLayout>
    </RestrictedRoute>
  );
}

export default LoginPage;
