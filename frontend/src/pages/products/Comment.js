import React, { useEffect, useState } from "react";
import TextareaAutosize from "@mui/base/TextareaAutosize";
import { Avatar, Box, Paper, Rating, Stack, Typography } from "@mui/material";
import { styled } from "@mui/system";
import ButtonBase from "../../components/button/ButtonBase";
import { axiosPublic } from "../../utils/https";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const blue = {
  100: "#DAECFF",
  200: "#b6daff",
  400: "#3399FF",
  500: "#007FFF",
  600: "#0072E5",
  900: "#003A75",
};

const grey = {
  50: "#f6f8fa",
  100: "#eaeef2",
  200: "#d0d7de",
  300: "#afb8c1",
  400: "#8c959f",
  500: "#6e7781",
  600: "#57606a",
  700: "#424a53",
  800: "#32383f",
  900: "#24292f",
};

const StyledTextarea = styled(TextareaAutosize)(
  ({ theme }) => `
    width: 100%;
    font-family: IBM Plex Sans, sans-serif;
    font-size: 0.875rem;
    font-weight: 400;
    line-height: 1.5;
    padding: 12px;
    border-radius: 12px 12px 0 12px;
    color: ${theme.palette.mode === "dark" ? grey[300] : grey[900]};
    background: ${theme.palette.mode === "dark" ? grey[900] : "#fff"};
    border: 1px solid ${theme.palette.mode === "dark" ? grey[700] : grey[200]};
    box-shadow: 0px 2px 24px ${
      theme.palette.mode === "dark" ? blue[900] : blue[100]
    };
  
    &:hover {
      border-color: ${blue[400]};
    }
  
    &:focus {
      border-color: ${blue[400]};
      box-shadow: 0 0 0 3px ${
        theme.palette.mode === "dark" ? blue[600] : blue[200]
      };
    }
  
    // firefox
    &:focus-visible {
      outline: 0;
    }
  `
);

function Comment({ idProduct }) {
  const axiosPrivate = useAxiosPrivate();
  const [rating, setRating] = useState(5);
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");

  // send comment and rating
  const handleSend = () => {
    if (!commentText) return;
    Promise.all([
      axiosPrivate.post(`/products/${idProduct}/ratings`, {
        rating: rating,
      }),
      axiosPrivate.post(`/products/${idProduct}/comments`, {
        comment: commentText,
        parentCommentId: null,
      }),
    ])
      .then(() => {
        getComment();
        setCommentText("");
        setRating(5);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // get comment
  const getComment = async () => {
    try {
      const resp = await axiosPublic.get(
        `/products/${idProduct}/comments/parent/null`
      );
      if (resp && resp.status === 200) {
        setComments(resp.data);
      }
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    if (idProduct) {
      getComment();
    }
  }, [idProduct]);

  return (
    <Stack spacing={1}>
      <Typography sx={{ fontSize: "14px", fontWeight: 550 }}>
        Bình luận
      </Typography>
      <StyledTextarea
        value={commentText}
        onChange={(e) => setCommentText(e.target.value)}
        minRows={2}
        placeholder="Viết bình luận về sản phẩm."
      />
      <Stack
        direction="row"
        justifyContent="space-between"
        alignItems="flex-start"
      >
        <Stack direction="row" alignItems="center" spacing={1}>
          <Typography>Đánh giá</Typography>
          <Rating
            value={rating}
            onChange={(_, newRating) => setRating(newRating)}
          />
        </Stack>
        <ButtonBase
          onClick={handleSend}
          classes="inline-block bg-black text-white"
        >
          Gửi
        </ButtonBase>
      </Stack>
      <Box
        sx={{
          mt: 1,
          height: "auto",
          overflow: "hidden",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Stack
          className="hidden-scroll"
          spacing={"5px"}
          sx={{
            width: "100%",
            maxHeight: "300px",
            overflow: "auto",
            padding: "10px 0",
          }}
        >
          {comments.map((comment) => (
            <CommentItem
              key={comment.id}
              comment={comment}
              idProduct={idProduct}
              getComment={getComment}
            />
          ))}
        </Stack>
        {/* <ButtonBase classes="bg-black text-white">
          Xem thêm bình luận
        </ButtonBase> */}
      </Box>
    </Stack>
  );
}

export default Comment;

const CommentItem = ({ idProduct, comment, getComment }) => {
  const axiosPrivate = useAxiosPrivate();
  const [openReply, setOpenReply] = useState(false);
  const [commentText, setCommentText] = useState("");

  const handleReply = async () => {
    const resp = await axiosPrivate.post(`/products/${idProduct}/comments`, {
      comment: commentText,
      parentCommentId: comment.id,
    });
    if (resp && resp.status === 200) {
      getComment();
    }
  };

  return (
    <Box>
      <Stack direction="row" spacing="5px" p="2px">
        <Avatar src="" sx={{ width: 30, height: 30 }} />
        <Paper sx={{ padding: "10px 10px 5px", flexGrow: 1 }}>
          <Typography sx={{ fontSize: "13px", fontWeight: 550 }}>
            {comment.user}
          </Typography>
          <Typography sx={{ fontSize: "13px", fontWeight: 400 }}>
            {comment.comment}
          </Typography>
          <Stack direction="row" justifyContent="flex-end" spacing={"5px"}>
            {openReply && (
              <Typography
                sx={{
                  fontSize: "12px",
                  padding: "2px",
                  borderRadius: "4px",
                  cursor: "pointer",
                  "&:hover": { backgroundColor: "#ededed" },
                }}
                onClick={handleReply}
              >
                Gửi
              </Typography>
            )}
            <Typography
              sx={{
                fontSize: "12px",
                padding: "2px",
                borderRadius: "4px",
                cursor: "pointer",
                "&:hover": { backgroundColor: "#ededed" },
              }}
              onClick={() => setOpenReply(!openReply)}
            >
              {openReply ? "Hủy" : "Trả lời"}
            </Typography>
          </Stack>
          {openReply && (
            <Box sx={{ mt: "5px" }}>
              <StyledTextarea
                value={commentText}
                onChange={(e) => setCommentText(e.target.value)}
                minRows={2}
                placeholder="Trả lời bình luận"
              />
            </Box>
          )}
        </Paper>
      </Stack>
    </Box>  
  );
};
