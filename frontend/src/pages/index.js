import { useRouter } from "next/router";
import { useEffect } from "react";

export default function HomeNavigate() {
  const router = useRouter();

  useEffect(() => {
    router.push("/home");
  }, []);

  return <div>Redirecting...</div>;
}
