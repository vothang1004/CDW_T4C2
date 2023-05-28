import React from "react";
import { MenuItem, Paper } from "@mui/material";
import { useRouter } from "next/router";
import Link from "next/link";

const activeLinkStyle = {
  backgroundColor: "primary.main",
  color: "#fff",
  "&:hover": {
    backgroundColor: "primary.main",
  },
};

function Sidebar() {
  const router = useRouter();
  return (
    <Paper sx={{ height: "100%" }}>
      <Link href="/admin/home">
        <MenuItem
          sx={router.pathname.indexOf("home") >= 0 ? activeLinkStyle : {}}
        >
          Trang chủ
        </MenuItem>
      </Link>
      <Link href="/admin/products">
        <MenuItem
          sx={router.pathname.indexOf("products") >= 0 ? activeLinkStyle : {}}
        >
          Sản phẩm
        </MenuItem>
      </Link>
      <Link href="/admin/categories">
        <MenuItem
          sx={router.pathname.indexOf("categories") >= 0 ? activeLinkStyle : {}}
        >
          Danh mục
        </MenuItem>
      </Link>
      <Link href="/admin/users">
        <MenuItem
          sx={router.pathname.indexOf("users") >= 0 ? activeLinkStyle : {}}
        >
          Người dùng
        </MenuItem>
      </Link>
    </Paper>
  );
}

export default Sidebar;
