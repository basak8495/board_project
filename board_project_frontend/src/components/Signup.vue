<template>
  <main>
    <el-row class="content" type="flex" justify="center">
      <el-row>
        <h3 class="title">회원가입</h3>
        <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm" label-width="120px">
          <el-form-item label="아이디" prop="loginId">
            <el-input v-model="ruleForm1.loginId"></el-input>
          </el-form-item>
          <el-form-item label="비밀번호" prop="password">
            <el-input v-model="ruleForm1.password" show-password></el-input>
          </el-form-item>
          <el-form-item label="닉네임" prop="nickname">
            <el-input v-model="ruleForm1.nickname"></el-input>
          </el-form-item>
        </el-form>
        <el-form :model="ruleForm2" ref="ruleForm" label-width="120px" class="demo-ruleForm">
          <el-form-item label="이름" prop="name">
            <el-input v-model="ruleForm2.name"></el-input>
          </el-form-item>
          <el-form-item label="이메일" prop="email">
            <el-input v-model="ruleForm2.email"></el-input>
          </el-form-item>
          <el-form-item label="핸드폰 번호" prop="phoneNumber">
            <el-input v-model="ruleForm2.phoneNumber"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm()">회원가입</el-button>
            <el-button @click="resetForm()">초기화</el-button>
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
        ruleForm1: {
          loginId: "",
          password: "",
          nickname: ""
        },
        ruleForm2: {
            name: "",
            email: "",
            phoneNumber: ""
        },
        rules1: {
          loginId: [
            { required: true, message: "아이디를 입력해주세요", trigger: "blur" },
            { min: 4, max: 16, message: "4자이상 16자이하로 입력해주세요", trigger: "blur" }
          ],
          password: [
            { required: true, message: "비밀번호를 입력해주세요", trigger: "blur" },
            { min: 4, max: 16, message: "4자이상 16자이하로 입력해주세요", trigger: "blur" }
          ],
          nickname: [
            { required: true, message: "닉네임을 입력해주세요", trigger: "blur" },
            { min: 2, max: 8, message: "2자이상 8자이하로 입력해주세요", trigger: "blur" }
          ]
        }
      };
    },
    methods: {
      submitForm() {
        const mergedForm = {
          ...this.ruleForm1,
          ...this.ruleForm2
        };

        // 회원가입
        this.$axios.post("/api/signup", mergedForm)
          .then(response => {
            alert("회원가입이 완료되었습니다!");
            console.log("회원가입이 완료되었습니다!");
            this.$router.push('/');
          })
          .catch(error => {
            alert("회원가입에 실패하였습니다.");
            console.log("회원가입에 실패하였습니다.");
          });
      },
      resetForm() {
        this.$refs.mergedForm.resetFields();
      },
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