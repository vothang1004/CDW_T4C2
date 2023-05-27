import React, { useState } from "react";
import { BsCart2 } from "react-icons/bs";
import { IconButton } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import ModalLogin from "../modal/ModalLogin";
import { asyncAddToCart } from "../../redux/actions/cart.action";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useAlertContext } from "../../contexts/alert/AlertProvider";
import ButtonBase from "../button/ButtonBase";

function AddToCart({ product, isDetail }) {
  const axiosPrivate = useAxiosPrivate();
  const dispatch = useDispatch();
  const login = useSelector((state) => state.auth.login);
  const { alert } = useAlertContext();
  const [openFormLogin, setOpenFormLogin] = useState(false);

  const handleAddToCart = async (e) => {
    e.stopPropagation();
    if (!login) {
      setOpenFormLogin(true);
    } else {
      const dataCartPost = {
        id: product.id,
        name: product.name,
        description: product.description,
        price: product.price,
      };
      await asyncAddToCart({
        data: dataCartPost,
        axios: axiosPrivate,
        alert,
        dispatch,
      });
    }
  };

  return (
    <>
      {openFormLogin && (
        <ModalLogin open={true} onClose={() => setOpenFormLogin(false)} />
      )}
      {isDetail ? (
        <ButtonBase
          onClick={handleAddToCart}
          classes="border border-black hover:text-white"
        >
          Thêm vào giỏ
        </ButtonBase>
      ) : (
        <IconButton
          onClick={handleAddToCart}
          sx={{
            position: "absolute",
            zIndex: 10,
            right: "10px",
            bottom: "0",
            backgroundColor: "primary.main",
            color: "#fff",
            "&:hover": {
              backgroundColor: "secondary.main",
            },
          }}
        >
          <BsCart2 size="14px" />
        </IconButton>
      )}
    </>
  );
}

export default AddToCart;
