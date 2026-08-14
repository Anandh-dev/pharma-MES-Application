import {
    BrowserRouter,
    Routes,
    Route,
    Navigate
} from "react-router-dom";

import Login from "../pages/auth/Login";
import Dashboard from "../pages/dashboard/Dashboard";
import BatchList from "../pages/batches/BatchList";
import BatchDetails from "../pages/batches/BatchDetails";
import CreateBatch from "../pages/batches/CreateBatch";
import EditBatch from "../pages/batches/EditBatch";
import ProductionOrderList from "../pages/production/ProductionOrderList";
import ProductionOrderDetails from "../pages/production/ProductionOrderDetails";
import CreateProductionOrder from "../pages/production/CreateProductionOrder";

function AppRoutes() {

    return (
        <BrowserRouter>

            <Routes>

            <Route
                path="/login"
                element={<Login />}
            />

            <Route
                path="/dashboard"
                element={<Dashboard />}
            />

            <Route
                path="/batches"
                element={<BatchList />}
            />

            <Route
                path="/batches/create"
                element={<CreateBatch />}
            />

            <Route
                path="/batches/:id"
                element={<BatchDetails />}
            />

            <Route
                path="/batches/:id/edit"
                element={<EditBatch />}
            />

            <Route
                path="/production"
                element={<ProductionOrderList />}
            />

            <Route
                path="/production/create"
                element={<CreateProductionOrder />}
            />

            <Route
                path="/production/:id"
                element={<ProductionOrderDetails />}
            />

            <Route
                path="/"
                element={
                    <Navigate
                        to="/login"
                        replace
                    />
                }
            />

            <Route
                path="*"
                element={
                    <Navigate
                        to="/login"
                        replace
                    />
                }
            />

            </Routes>

        </BrowserRouter>
    );
}

export default AppRoutes;