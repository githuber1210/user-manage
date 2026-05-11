import { createRouter, createWebHistory } from 'vue-router'

import UserList from '../views/UserList.vue'
import AddUser from '../views/AddUser.vue'
import EditUser from '../views/EditUser.vue'

import RoleList from '../views/role/RoleList.vue'
import AddRole from '../views/role/AddRole.vue'
import EditRole from '../views/role/EditRole.vue'

import MajorList from '../views/major/MajorList.vue'
import AddMajor from '../views/major/AddMajor.vue'
import EditMajor from '../views/major/EditMajor.vue'

import ClassList from '../views/class/ClassList.vue'
import AddClass from '../views/class/AddClass.vue'
import EditClass from '../views/class/EditClass.vue'

import TeacherList from '../views/teacher/TeacherList.vue'
import AddTeacher from '../views/teacher/AddTeacher.vue'
import EditTeacher from '../views/teacher/EditTeacher.vue'

import StudentList from '../views/student/StudentList.vue'
import AddStudent from '../views/student/AddStudent.vue'
import EditStudent from '../views/student/EditStudent.vue'

const routes = [
  {
    path: '/',
    name: 'UserList',
    component: UserList
  },
  {
    path: '/users',
    name: 'UserListPage',
    component: UserList
  },
  {
    path: '/users/add',
    name: 'AddUser',
    component: AddUser
  },
  {
    path: '/users/edit/:id',
    name: 'EditUser',
    component: EditUser
  },
  {
    path: '/roles',
    name: 'RoleList',
    component: RoleList
  },
  {
    path: '/roles/add',
    name: 'AddRole',
    component: AddRole
  },
  {
    path: '/roles/edit/:id',
    name: 'EditRole',
    component: EditRole
  },
  {
    path: '/majors',
    name: 'MajorList',
    component: MajorList
  },
  {
    path: '/majors/add',
    name: 'AddMajor',
    component: AddMajor
  },
  {
    path: '/majors/edit/:id',
    name: 'EditMajor',
    component: EditMajor
  },
  {
    path: '/classes',
    name: 'ClassList',
    component: ClassList
  },
  {
    path: '/classes/add',
    name: 'AddClass',
    component: AddClass
  },
  {
    path: '/classes/edit/:id',
    name: 'EditClass',
    component: EditClass
  },
  {
    path: '/teachers',
    name: 'TeacherList',
    component: TeacherList
  },
  {
    path: '/teachers/add',
    name: 'AddTeacher',
    component: AddTeacher
  },
  {
    path: '/teachers/edit/:id',
    name: 'EditTeacher',
    component: EditTeacher
  },
  {
    path: '/students',
    name: 'StudentList',
    component: StudentList
  },
  {
    path: '/students/add',
    name: 'AddStudent',
    component: AddStudent
  },
  {
    path: '/students/edit/:id',
    name: 'EditStudent',
    component: EditStudent
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
