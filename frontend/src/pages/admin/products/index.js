import React, { useEffect, useState } from "react";
import AdminLayout from "../../../components/layouts/admin/AdminLayout";
import { Paper, Typography } from "@mui/material";
import PrivateRoute from "../../../components/route/PrivateRoute";
import AdminRequiredRoute from "../../../components/route/AdminRequiredRoute";
import TableBase from "../../../components/table/TableBase";
import { axiosPublic } from "../../../utils/https";

const columns = [
  {
    name: "ID",
    selector: (row) => row.id,
    sortable: true,
    width: "70px",
  },
  {
    name: "Tên",
    selector: (row) => row.name,
    sortable: true,
    minWidth: "200px",
  },
  {
    name: "Danh mục",
    selector: (row) => row.category.name,
    sortable: true,
    width: "150px",
  },
  {
    name: "Giá",
    selector: (row) => row.price,
    sortable: true,
    width: "100px",
  },
  {
    name: "Mô tả",
    selector: (row) => row.description,
    sortable: true,
    maxWidth: "400px",
    wrap: true,
  },
];

function AdminProducts() {
  const [products, setProducts] = useState([]);
  const [paginationOptions, setPaginationOptions] = useState({
    totalRows: 0,
    page: 1,
    rowPerPage: 20,
  });

  // get product
  const getProduct = async () => {
    try {
      const resp = await axiosPublic.get(
        `/products?limit=${paginationOptions.rowPerPage}&page=${paginationOptions.page}`
      );
      if (resp && resp.status === 200) {
        setProducts(resp.data.content);
        setPaginationOptions({
          ...paginationOptions,
          totalRows: resp.data.totalElements,
        });
      }
    } catch (error) {
      console.log({ error });
    }
  };

  useEffect(() => {
    getProduct();
  }, [paginationOptions.page, paginationOptions.rowPerPage]);

  return (
    <PrivateRoute>
      <AdminRequiredRoute>
        <AdminLayout>
          <Paper sx={{ height: "100%", padding: "10px" }}>
            <Typography sx={{ paddingBottom: "10px" }}>
              Danh sách sản phẩm
            </Typography>
            <TableBase
              data={products}
              columns={columns}
              pagination
              paginationPerPage={paginationOptions.rowPerPage}
              paginationTotalRows={paginationOptions.totalRows}
              onChangePage={(newPage) => {
                setPaginationOptions({ ...paginationOptions, page: newPage });
              }}
              onChangeRowsPerPage={(value) =>
                setPaginationOptions({
                  ...paginationOptions,
                  rowPerPage: value,
                })
              }
            />
          </Paper>
        </AdminLayout>
      </AdminRequiredRoute>
    </PrivateRoute>
  );
}

export default AdminProducts;
