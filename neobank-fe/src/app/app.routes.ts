import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [

  // HOME PAGE
  {
    path: '',
    loadComponent: () =>
      import('./home/home.component').then(m => m.HomeComponent)
  },

  // AUTH
  {
    path: 'login',
    loadComponent: () =>
      import('./auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./auth/register/register.component').then(m => m.RegisterComponent)
  },

  // ADMIN
  {
    path: 'admin',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./admin/admin.component').then(m => m.AdminComponent)
  },

  {
    path: 'admin/dashboard',
    canActivate: [authGuard, adminGuard],
    loadComponent: () =>
      import('./admin/dashboard/admin-dashboard.component').then(m => m.AdminDashboardComponent)
  },

  {
    path: 'admin/account-requests',
    canActivate: [authGuard, adminGuard],
    loadComponent: () =>
      import('./admin/admin-requests/account-requests.component').then(m => m.AccountRequestsComponent)
  },

  {
    path: 'admin/users',
    canActivate: [authGuard, adminGuard],
    loadComponent: () =>
      import('./admin/users/users.component').then(m => m.AdminUsersComponent)
  },

  {
    path: 'admin/loan-products',
    canActivate: [authGuard, adminGuard],
    loadComponent: () =>
      import('./admin/loans/loan-products/loan-products.component').then(m => m.LoanProductsComponent)
  },

  
{
  path: 'admin/loan-products/create',
  canActivate: [authGuard, adminGuard],
  loadComponent: () =>
    import('./admin/loans/create-loan-product/create-loan-product.component')
      .then(m => m.CreateLoanProductComponent)
},


  // CUSTOMER
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./dashboard/dashboard.component').then(m => m.DashboardComponent)
  },

  {
    path: 'open-account',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./account-opening/account-opening.component').then(m => m.AccountOpeningComponent)
  },

  {
    path: 'accounts',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./accounts/accounts.component').then(m => m.AccountsComponent)
  },

  {
    path: 'transactions/:accountId',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./transactions/transactions.component').then(m => m.TransactionsComponent)
  },

  {
    path: 'budget',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./budget/dashboard/dashboard.component').then(m => m.BudgetDashboardComponent)
  },

  {
    path: 'budget/create',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./budget/create/create.component').then(m => m.BudgetCreateComponent)
  },

  {
    path: 'bills',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./bills/dashboard/dashboard.component').then(m => m.BillsDashboardComponent)
  },

  {
    path: 'bills/create',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./bills/create/create.component').then(m => m.BillCreateComponent)
  },

  {
    path: 'rewards',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./rewards/dashboard/dashboard.component').then(m => m.RewardsDashboardComponent)
  },

  {
    path: 'loan/my-loans',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./loan/my-loans/my-loans.component').then(m => m.MyLoansComponent)
  },

  {
    path: 'loan/apply',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./loan/apply/apply.component').then(m => m.ApplyComponent)
  },

  {
    path: 'loan/repayment/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./loan/repayment/repayment.component').then(m => m.RepaymentComponent)
  },

  {
    path: 'insights',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./insights/insights.component').then(m => m.InsightsComponent)
  },

  {
    path: 'admin/loan-decisions',
    canActivate: [authGuard, adminGuard],
    loadComponent: () =>
      import('./admin/loans/loan-decisions/loan-decisions.component').then(m => m.LoanDecisionsComponent)
  },
  
  {
    path: 'admin/analytics',
    canActivate: [authGuard, adminGuard],
    loadComponent: () =>
      import('./admin/analytics/analytics.component').then(m => m.AnalyticsComponent)
  },

  {
    path: 'admin/system-health',
    canActivate: [authGuard, adminGuard],
    loadComponent: () =>
      import('./admin/system-health/system-health.component').then(m => m.SystemHealthComponent)
  },


  // FALLBACK
  {
    path: '**',
    redirectTo: ''
  }
];
