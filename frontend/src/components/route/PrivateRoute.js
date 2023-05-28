import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { useRouter } from "next/router";

function PrivateRoute({ children }) {
  const router = useRouter();
  const login = useSelector((state) => state.auth.login);
  useEffect(() => {
    if (!login) {
      router.push("/login");
    }
  }, [login]);
  return <div>{children}</div>;
}

export default PrivateRoute;
