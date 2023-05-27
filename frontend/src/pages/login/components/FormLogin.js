import React from "react";
import ButtonBase from "../../../components/button/ButtonBase";
import InputBase from "../../../components/input/InputBase";
import Link from "next/link";
import * as yup from "yup";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { useDispatch } from "react-redux";
import { useRouter } from "next/router";
import { loginUser } from "../../../redux/actions/auth.action";
import { useAlertContext } from "../../../contexts/alert/AlertProvider";

const schema = yup.object({
  email: yup
    .string()
    .required("Vui lòng nhập email")
    .email("Email không đúng định dạng"),
  password: yup.string().required("Vui lòng nhập mật khẩu"),
});

function FormLogin({ onCloseModal }) {
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
  const { alert } = useAlertContext();

  // on submit
  const onSubmit = (values) => {
    return new Promise((resovle) => {
      setTimeout(async () => {
        await loginUser({
          dispatch,
          router: onCloseModal ? undefined : router,
          pathAfterSuccess: "/home",
          data: values,
          alert
        });
        onCloseModal?.();
        resovle();
      }, 500);
    });
  };

  return (
    <div>
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
            type="password"
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
        <Link href="/register">
          <span className="text-[12px] hover:text-red cursor-pointer">
            Đăng ký tại đây
          </span>
        </Link>
      </div>
    </div>
  );
}

export default FormLogin;
