import React from "react";
import ButtonBase from "../../components/button/ButtonBase";
import InputBase from "../../components/input/InputBase";
import MainLayout from "../../components/layouts/MainLayout";
import RestrictedRoute from "../../components/route/RestrictedRoute";
import Image from "next/image";
import Head from "next/head";
import FacebookBtnImage from "../../assets/images/fb_btn.svg";
import GoogleBtnImage from "../../assets/images/gg_btn.svg";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import Link from "next/link";
import { useRouter } from "next/router";
import { registerUser } from "../../redux/actions/auth.action";

const schema = yup.object({
  username: yup.string().required("Vui lòng nhập tên đăng nhập"),
  email: yup
    .string()
    .required("Vui lòng nhập email")
    .email("Email không đúng định dạng"),
  password: yup.string().required("Vui lòng nhập mật khẩu"),
});

function RegisterPage() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm({
    mode: "onBlur",
    resolver: yupResolver(schema),
  });
  const router = useRouter();

  // on submit
  const onSubmit = (values) => {
    return new Promise((resovle) => {
      setTimeout(() => {
        registerUser({ data: values, router });
        resovle();
      }, 500);\
    });
  };

  return (
    <RestrictedRoute>
      <MainLayout>
        <Head>
          <title>Đăng ký</title>
        </Head>
        <div className="container min-h-[calc(100vh-60px-40px-100px)] px-4 py-[45px] flex items-center justify-center">
          <div className="shadow-md p-[15px] rounded-md w-[410px]">
            <h5 className="text-center text-xl uppercase">Đăng ký tài khoản</h5>
            <form
              onSubmit={handleSubmit(onSubmit)}
              className="mt-5 flex flex-col gap-[10px]"
            >
              <div className="flex flex-col gap-[5px]">
                <InputBase
                  label="Tên đăng nhập"
                  placeholder="Tên đăng nhập của bạn"
                  required
                  name="username"
                  register={register}
                  errorMessage={errors?.username?.message}
                />
              </div>
              <div className="flex flex-col gap-[5px]">
                <InputBase
                  label="Email"
                  placeholder="Nhập email đăng ký"
                  required
                  name="email"
                  register={register}
                  errorMessage={errors?.email?.message}
                />
              </div>
              <div className="flex flex-col gap-[5px]">
                <InputBase
                  type="password"
                  name="password"
                  register={register}
                  label="Mật khẩu"
                  placeholder="Mật khẩu"
                  required
                  errorMessage={errors?.password?.message}
                />
              </div>
              <div className="flex flex-col gap-[5px]">
                <InputBase
                  name="phoneNumber"
                  register={register}
                  label="Số điện thoại"
                  placeholder="VD: 0359499339"
                />
              </div>
              <div className="flex flex-col gap-[5px]">
                <InputBase
                  name="dob"
                  type="date"
                  register={register}
                  label="Ngày sinh"
                />
              </div>
              <div className="flex flex-col gap-[5px]">
                <span className="text-[12px]">Giới tính</span>
                <div className="flex items-center gap-10">
                  <div className="flex items-center gap-2 cursor-pointer">
                    <input
                      id="gender_nu"
                      type="radio"
                      value="female"
                      name="gender"
                      className="accent-red"
                      {...register("gender")}
                    />
                    <label htmlFor="gender_nu" className="text-[12px]">
                      Nữ
                    </label>
                  </div>
                  <div className="flex items-center gap-2 cursor-pointer">
                    <input
                      id="gender_nam"
                      type="radio"
                      value="male"
                      name="gender"
                      className="accent-red"
                      {...register("gender")}
                    />
                    <label htmlFor="gender_nam" className="text-[12px]">
                      Nam
                    </label>
                  </div>
                  <div className="flex items-center gap-2 cursor-pointer">
                    <input
                      id="gender_other"
                      type="radio"
                      value="other"
                      name="gender"
                      className="accent-red"
                      {...register("gender")}
                    />
                    <label htmlFor="gender_other" className="text-[12px]">
                      Khác
                    </label>
                  </div>
                </div>
              </div>
              <ButtonBase
                type="submit"
                fullWidth
                loading={isSubmitting}
                classes="bg-black text-white hover:bg-red"
              >
                Đăng ký
              </ButtonBase>
            </form>
            <div className="flex items-center justify-between mt-2">
              <span className="text-[12px] hover:text-red cursor-pointer">
                Bạn đã có tài khoản ?
              </span>
              <Link href="/login">
                <span className="text-[12px] hover:text-red cursor-pointer">
                  Đăng nhập tại đây
                </span>
              </Link>
            </div>
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

export default RegisterPage;
