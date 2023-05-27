import React, { useEffect, useState } from "react";
import { FiUser } from "react-icons/fi";
import { GoLocation } from "react-icons/go";
import { BsTelephone, BsCart2 } from "react-icons/bs";
import { BiChevronDown } from "react-icons/bi";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Link from "next/link";
import SearchBar from "./SearchBar";
import { useDispatch, useSelector } from "react-redux";
import { useRouter } from "next/router";
import { useAlertContext } from "../../../contexts/alert/AlertProvider";
import { asyncGetCart } from "../../../redux/actions/cart.action";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { logoutUser } from "../../../redux/actions/auth.action";

function Header({ onSearch, defaultSearchText }) {
  const login = useSelector((state) => state.auth.login);
  const listCart = useSelector((state) => state.cart.listCart);
  const { alert } = useAlertContext();
  const axiosPrivate = useAxiosPrivate();
  const dispatch = useDispatch();
  const router = useRouter();
  const [anchorMenu, setAnchorMenu] = useState(null);

  // handle logout
  const handleLogout = () => {
    setAnchorMenu(null);
    logoutUser({ dispatch, alert });
  };
  useEffect(() => {
    asyncGetCart({ alert, axios: axiosPrivate, dispatch });
  }, []);

  return (
    <>
      <Menu
        anchorEl={anchorMenu}
        open={!!anchorMenu}
        onClose={() => setAnchorMenu(null)}
      >
        <MenuItem>Thông tin tài khoản</MenuItem>
        <MenuItem onClick={handleLogout}>Đăng xuất</MenuItem>
      </Menu>
      <header className="w-full h-[60px] text-white bg-black relative z-50">
        <div className="container mx-auto px-4 h-full flex items-center justify-between">
          <Link href="/home">
            <p className="text-[30px] font-bold cursor-pointer">Sudes Phone</p>
          </Link>
          <SearchBar
            onSearch={onSearch}
            defaultSearchText={defaultSearchText}
          />
          <div className="grow-1 flex gap-4 items-center justify-between">
            <div className="flex items-center gap-2">
              <div className="h-[40px] text-white flex gap-1 items-center bg-black-gray p-1 rounded cursor-pointer hover:brightness-90">
                <GoLocation size="14px" />
                <div className="flex flex-col gap-1">
                  <p className="text-[8px] leading-[8px]">Hệ thống cửa hàng</p>
                  <p className="text-[10px] leading-[10px] font-semibold">
                    7 cửa hàng
                  </p>
                </div>
              </div>
            </div>
            <div className="flex items-center gap-2">
              <div className="h-[40px] text-white flex gap-1 items-center p-1 rounded cursor-pointer hover:brightness-90">
                <BsTelephone size="14px" />
                <div className="flex flex-col gap-1">
                  <p className="text-[8px] leading-[8px]">Gọi mua hàng</p>
                  <p className="text-[10px] leading-[10px] font-semibold">
                    1900 6750
                  </p>
                </div>
              </div>
            </div>
            <div className="flex items-center gap-2">
              <div className="h-[40px] text-white flex gap-1 items-center p-1 rounded cursor-pointer hover:brightness-90">
                <FiUser size="14px" />
                {login?.user ? (
                  <>
                    <div
                      onClick={(e) => setAnchorMenu(e.currentTarget)}
                      className="flex flex-col gap-1"
                    >
                      <p className="text-[8px] leading-[8px]">Xin Chào</p>
                      <p className="text-[10px] leading-[10px] font-semibold">
                        {login?.user?.username || "tài khoản"}
                      </p>
                    </div>
                  </>
                ) : (
                  <div
                    onClick={() => router.push("/login")}
                    className="flex flex-col gap-1"
                  >
                    <p className="text-[8px] leading-[8px]">Đăng nhập</p>
                    <p className="text-[10px] leading-[10px] font-semibold">
                      tài khoản
                    </p>
                  </div>
                )}

                <BiChevronDown size="14px" />
              </div>
            </div>
            <div className="flex items-center gap-2">
              <div className="h-[40px] text-white flex gap-1 items-center p-1 rounded cursor-pointer hover:brightness-90">
                <span className="relative">
                  <span
                    className="absolute top-0 right-0 translate-x-1/2 -translate-y-1/2 inline-flex
                items-center justify-center w-4 h-4 rounded-full bg-red text-white text-[8px] border border-solid border-white"
                  >
                    {listCart?.productsWithAmount
                      ? Object.keys(listCart?.productsWithAmount).length
                      : 0}
                  </span>
                  <BsCart2 size="20px" />
                </span>
                <Link href="/cart">
                  <p className="text-[10px] leading-[10px]">Giỏ hàng</p>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </header>
    </>
  );
}

export default Header;
