<template>
  <main>
    <el-row class="content" type="flex" justify="center">
      <el-row>
        <h3 class="title">로그인</h3>
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="120px">
          <el-form-item label="아이디" prop="loginId">
              <el-input v-model="loginForm.loginId"></el-input>
          </el-form-item>
          <el-form-item label="비밀번호" prop="password">
              <el-input v-model="loginForm.password" show-password></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="submitForm(loginForm)">로그인</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </el-row>
  </main>
</template>

<script>
  export default {
    data() {
      return {
        loginForm: {
          loginId: "",
          password: ""
        },
        rules: {
          loginId: [
            { required: true, message: "아이디를 입력해주세요", trigger: "blur" },
            { min: 4, max: 16, message: "4자이상 16자이하로 입력해주세요", trigger: "blur" }
          ],
          password: [
            { required: true, message: "비밀번호를 입력해주세요", trigger: "blur" },
            { min: 4, max: 16, message: "4자이상 16자이하로 입력해주세요", trigger: "blur" }
          ]
        }
      };
    },
    methods: {
      submitForm() {
        // 로그인
        this.$http.post("/api/login", this.loginForm, { withCredentials: true })
          .then(response => {
            // JWT Token decode
            localStorage.setItem("jwtToken", response.data);

            const isLoggedIn = true;
            this.$router.push({ path: "/", props: { isLoggedIn } })

            alert("로그인에 성공했습니다!");
            console.log("로그인에 성공했습니다!");
            console.log("JWT 토큰값:", response.data);

            this.$router.push('/');
            window.location.reload();
          })
          .catch(error => {
            alert("로그인에 실패했습니다.");
            console.error("로그인에 실패했습니다.", error);
          });
      }
    }
  }
</script>

<style scoped>
  .content {
    margin-top: 2%;
  }
  .title {
    text-align: center;
  }
</style>