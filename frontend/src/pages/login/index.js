import React from "react";
import ButtonBase from "../../components/button/ButtonBase";
import InputBase from "../../components/input/InputBase";
import MainLayout from "../../components/layouts/MainLayout";
import RestrictedRoute from "../../components/route/RestrictedRoute";
import Image from "next/image";
import FacebookBtnImage from "../../assets/images/fb_btn.svg";
import GoogleBtnImage from "../../assets/images/gg_btn.svg";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { loginUser } from "../../redux/actions/auth.action";
import { useDispatch } from "react-redux";
import { useRouter } from "next/router";

const schema = yup.object({
  email: yup
    .string()
    .required("Vui lòng nhập email")
    .email("Email không đúng định dạng"),
  password: yup.string().required("Vui lòng nhập mật khẩu"),
});

function LoginPage() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm({
    mode: "onBlur",
    resolver: yupResolver(schema),
  });
  const dispatch = useDispatch();
  const router = useRouter();

  // on submit
  const onSubmit = (values) => {
    return new Promise((resovle) => {
      setTimeout(() => {
        loginUser({ dispatch, router, data: values });
        resovle();
      }, 500);
    });
  };

  return (
    <RestrictedRoute>
      <MainLayout>
        <div className="container min-h-[calc(100vh-60px-40px-100px)] px-4 py-[45px] flex items-center justify-center">
          <div className="shadow-md p-[15px] rounded-md w-[410px]">
            <h5 className="text-center text-xl uppercase">Đăng nhập</h5>
            <form
              onSubmit={handleSubmit(onSubmit)}
              className="mt-5 flex flex-col gap-[10px]"
            >
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
                  name="password"
                  register={register}
                  label="Mật khẩu"
                  placeholder="Mật khẩu"
                  required
                  errorMessage={errors?.password?.message}
                />
              </div>
              <ButtonBase
                classes="bg-black text-white"
                type="submit"
                fullWidth
                loading={isSubmitting}
              >
                Đăng nhập
              </ButtonBase>
            </form>
            <div className="flex items-center justify-between mt-2">
              <span className="text-[12px] hover:text-red cursor-pointer">
                Quên mật khẩu ?
              </span>
              <span className="text-[12px] hover:text-red cursor-pointer">
                Đăng ký tại đây
              </span>
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

export default LoginPage;
