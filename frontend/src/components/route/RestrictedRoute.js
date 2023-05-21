import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { useRouter } from "next/router";

function RestrictedRoute({ children }) {
  const login = useSelector((state) => state.auth.login);
  const router = useRouter();

  useEffect(() => {
    if (login?.user) {
      router.push("/home");
    }
  }, [login]);
  return <>{children}</>;
}

export default RestrictedRoute;
