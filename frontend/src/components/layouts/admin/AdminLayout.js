import React from "react";
import AppBarHeader from "./AppBarHeader";
import { Container, Grid } from "@mui/material";
import Sidebar from "./Sidebar";

function AdminLayout({ children }) {
  return (
    <>
      <AppBarHeader />
      <Container
        maxWidth="xl"
        sx={{ padding: "10px 0", height: "calc(100vh - 63.91px)" }}
      >
        <Grid container spacing={2} sx={{ height: "100%" }}>
          <Grid item xs={2}>
            <Sidebar />
          </Grid>
          <Grid item xs={10}>
            {children}
          </Grid>
        </Grid>
      </Container>
    </>
  );
}

export default AdminLayout;
