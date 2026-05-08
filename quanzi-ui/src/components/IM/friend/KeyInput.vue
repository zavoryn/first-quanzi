<template>
  <div>
    <el-dialog title="群发消息" v-if="visible" :visible="visible" width="920px" :before-close="onClose"  :close-on-click-modal="false">
      <div class="mass-input"  @click="closeRefBox()" :style="{'height' : showType == 'pushWork' ? '420px' : '320px'}">
        <div class="mass-desc" v-if="showType == 'sendSeasMsg'" style="margin: 5px 0px 0px">
          <span style="font-size: 14px;color: #000; font-weight: 500;">
            <span style="color: #ff0000;">*</span>
            账号选择：</span>
          <el-select v-model="checkAccountIds" placeholder="请选择"  size="small" multiple  clearable style="width: 50%;">
            <el-option v-for="item in userList" :key="item.id" :label="item.nickName"  :value="item.id" />
          </el-select>
        </div>
        <div class="mass-desc" v-if="showType == 'sendSeasMsg' || showType == 'pushWork'" style="margin: 5px 0px 5px 9px;">
          <span style="font-size: 14px;color: #000; font-weight: 500;">
            <!-- <span style="color: #ff0000;">*</span> -->
            关键词句：</span>
          <el-input v-model="keyWord" placeholder="请输入" size="small" style="width: 50%; margin-right: 5px;" /> 
          <el-button type="info" plain  size="mini" :loading="waLoading"  @click="handleGenerate('文案')" >生成文案</el-button>
          <el-button type="primary" plain  size="mini" :loading="tpLoading"  @click="handleGenerate('图文')" >生成图文</el-button>
          <el-button type="success" plain  size="mini" :loading="spLoading" @click="handleGenerate('视频')" >生成视频</el-button>
        </div>
        <div class="mass-desc" v-if="showType == 'pushWork'" style="margin: 5px 0px 40px 9px; flex-direction: column; align-items: flex-start;">
          <div style="width: 100%;">
            <span style="font-size: 14px;color: #000; font-weight: 500;">
              文案标题：</span>
            <el-input v-model="title" placeholder="请输入" size="small" style="width: 80%; margin-right: 5px;" />
          </div>
          <div style="width: 100%; margin: 5px 0px 0px;">
            <span style="font-size: 14px;color: #000; font-weight: 500;">
              文案话题：</span>
            <el-input v-model="topic" placeholder="请输入" size="small" style="width: 80%; margin-right: 5px;" /> 
          </div>
        </div>
        <!-- <el-footer height="280px" class="im-chat-footer">
          <div class="chat-tool-bar">
            <div title="表情" class="icon iconfont icon-emoji" ref="emotion" @click.stop="showEmotionBox()">
            </div>
            <div title="发送图片" v-if="menus.includes('image')">
              <input type="file" ref="imageInput" @change="onFileChange"
                accept="image/jpeg, image/png, image/jpg, image/webp, image/gif" style="display: none;" />
              <i class="el-icon-picture-outline" @click="imageInput"></i>
            </div>

            <div title="发送视频" v-if="menus.includes('video')" class="file-video">
              <input type="file" ref="videoInput" @change="onFileChange" accept="video/mp4, video/mov"
                style="display: none;" />
              <img src="@/assets/im/image/video.png" alt="" style="width: 24px; height: 24px;" @click="videoInput" />
              <file-upload v-show="false" ref="videoUpload" :fileTypes="['video/mp4', 'video/mov']"
                :action="'/system/im/file/upload'" :maxSize="300 * 1024 * 1024">
                <img src="@/assets/im/image/video.png" alt="" style="width: 24px; height: 24px;" />
              </file-upload>
            </div>

            <div title="发送文件" v-if="menus.includes('file')">
              <input type="file" ref="fileInput" @change="onFileChange" style="display: none;" />
              <i class="el-icon-wallet" @click="fileInput"></i>
              <file-upload v-show="false" ref="fileUpload" :action="'/system/im/file/upload'"
                :maxSize="10 * 1024 * 1024">
                <i class="el-icon-wallet"></i>
              </file-upload>
            </div>

            <div title="录音文件" v-if="menus.includes('audioFile')" class="file-audio">
              <input type="file" ref="audioInput" @change="onFileChange" accept="audio/mpeg, audio/wav"
                style="display: none;" />
              <img src="@/assets/im/image/audio.png" alt="" style="width: 24px; height: 24px;" @click="audioInput" />
              <file-upload v-show="false" ref="audioUpload" :action="'/system/im/file/upload'"
                :maxSize="10 * 1024 * 1024">
                <i class="el-icon-wallet"></i>
              </file-upload>
            </div>

            <div title="发送语音" v-if="menus.includes('audio')" class="el-icon-microphone" @click="showRecordBox()">
            </div>

            <div title="发送视频号" v-if="menus.includes('wxVideo')" @click="showVideoBox()" style="display: flex; align-items: center;">
              <img src="@/assets/im/image/wx-video.png" alt="" style="width: 22px; height: 22px;" />
            </div>
          </div>
          <div class="send-content-area">
            <ChatInput ref="chatInputEditor" :openType="1" @parseFile="parseFile" @submit="sendMessage" />
          </div>
        </el-footer> -->
      </div>
      <!-- <div class="file-border">
        <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul" >
          <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in imageData">
            <el-link :href="file.url" :underline="false" target="_blank" style="height: 70px;width: 80%;justify-content: left;">
              <div style="display:flex;align-items: center;justify-content: left;width: 100%;" >
                <img src="@/assets/images/file_qt.png" style="width: 60px;height: 60px;">
                <div style="" class="yichu">{{ file.file.name }}
                </div>
              </div>
            </el-link>
            <div class="ele-upload-list__item-content-action" >
              <el-link :underline="false" @click="deleteFile(imageData,index)" type="danger">删除</el-link>
            </div>
          </li>
        </transition-group>

        <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul" >
          <li :key="file.fileId" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in fileData">
            <el-link :href="file.fileId" :underline="false" target="_blank" style="height: 70px;width: 80%;justify-content: left;">
              <div style="display:flex;align-items: center;justify-content: left;width: 100%;" >
                <img src="@/assets/images/file_qt.png" style="width: 60px;height: 60px;">
                <div style="" class="yichu">{{ file.file.name }}
                </div>
              </div>
            </el-link>
            <div class="ele-upload-list__item-content-action" >
              <el-link :underline="false" @click="deleteFile(fileData,index)" type="danger">删除</el-link>
            </div>
          </li>
        </transition-group>

        <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul" >
          <li :key="file.fileId" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in otherData">
            <el-link :href="file.fileId" :underline="false" target="_blank" style="height: 70px;width: 80%;justify-content: left;">
              <div style="display:flex;align-items: center;justify-content: left;width: 100%;" >
                <img src="@/assets/images/file_qt.png" style="width: 60px;height: 60px;">
                <div style="" class="yichu">{{ file.file.name }}
                </div>
              </div>
            </el-link>
            <div class="ele-upload-list__item-content-action" >
              <el-link :underline="false" @click="deleteFile(otherData,index)" type="danger">删除</el-link>
            </div>
          </li>
        </transition-group>
      </div> -->

      <!-- <emotion ref="emoBox" @emotion="onEmotion"></Emotion> -->
      <!-- <span slot="footer" class="dialog-footer">
        <el-button @click="onClose()">取 消</el-button>
        <el-button :loading="submitLoading" type="success"
          @click="notifySend()">发送</el-button>
      </span> -->
    </el-dialog>

    <!-- <chat-record :visible="showRecord" @close="closeRecordBox" @send="(item) => onSendData(item,3)"></chat-record>
    <chat-video :visible="showVideo" @close="closeVideoBox" @send="(item) => onSendData(item,5)"></chat-video> -->

  </div>
</template>

<script>
  import {
    listUserApi,
    handleUser,
    generateWork
  } from '@/api/cowork/accountmanager/index'
  //import SearchUser from './SearchUser.vue';
  //import Emotion from "@/components/IM/common/Emotion.vue";
  //import FileUpload from "@/components/IM/common/FileUpload.vue";
  //import ChatInput from "@/components/IM/chat/ChatInput";
  //import ChatBox from "@/components/IM/chat/ChatBox.vue";
  //import ChatRecord from "@/components/IM/chat/ChatRecord.vue";
  //import ChatVideo from "@/components/IM/chat/ChatVideo.vue";
  // import {
  //   basePngApi
  // } from "@/api/im-box/imTask"
  // import {
  //   mapState
  // } from "vuex";
  export default {
    name: "MassMessage",
    components: {
      // SearchUser,
      // Emotion,
      // FileUpload,
      // ChatInput,
      // ChatBox,
      // ChatRecord,
      // ChatVideo,
    },
    props: {
      visible: {
        type: Boolean
      },
      menus: {
        type: Array,
        default: function() {
          return ['image','video','file','audio','audioFile','wxVideo'];
        }
      },
      showType: {
        type: String,
        default: function() {
          return "sendSeasMsg";
        }
      },
      showPlatform: {
        type: String,
        default: function() {
          return "";
        }
      },
      accountIds: {
        type: Array,
        default: function() {
          return [];
        }
      },
      friendIds:{
        type: Array,
        default: function() {
          return [];
        }
      },
      friendUsers:{
        type: Array,
        default: function() {
          return [];
        }
      }
    },
    
    watch: {
      visible: function(newData, oldData) {
        if (newData) {
        }
      },
      accountIds: {
        handler(newVal) {
        },
        immediate: true
      }
    },
    data() {
      return {
        keyWord: "",
        title: "",
        topic: "",
        friends: [],
        lockMessage: false, // 是否锁定发送，
        reqQueue: [],
        isSending: false,
        //消息数组封装
        modelList: [],

        showVideo: false, // 是否显示视频号弹窗
        showRecord: false, // 是否显示语音录制弹窗

        imageData: [],
        currentImgId: 0,
        fileData: [],
        currentFileId: 0,
        //微信视频号 or 录音
        otherData: [],
        currentOtherId: 0,

        submitLoading: false,
        userList: [],
        checkAccountIds: [],

        waLoading: false,
        tpLoading: false,
        spLoading: false,
      }
    },
    computed: {
      //标签
      // ...mapState({
      //   windowHeight: state => state.settings.windowHeight
      // }),
      // listHeight() {
      //   if (!this.blurs) {
      //     return this.windowHeight - 600
      //   } else {
      //     return this.windowHeight - 600 - 114
      //   }
      // },
      // mine() {
      //   return this.$store.state.userStore.userInfo;
      // },
    },
    created(){
      if(this.showType == 'sendSeasMsg'){
        //获取设备列表
        this.getImCorpUser();
      }
    },
    methods: {
      getImCorpUser(){
        listUserApi({
          page: 1,
          pageSize: 1000,
          status: 0,
          corpName: this.showPlatform,
        }).then(res=>{
          this.userList = res.data.items;
        })
      },
      onClose() {
        this.$emit("close");
      },
      imageInput() {
        this.$refs.imageInput.click(); // 触发隐藏的文件输入框
      },
      fileInput() {
        this.$refs.fileInput.click(); // 触发隐藏的文件输入框
      },
      videoInput() {
        this.$refs.videoInput.click(); // 触发隐藏的文件输入框
      },
      audioInput() {
        this.$refs.audioInput.click(); // 触发隐藏的文件输入框
      },
      parseFile(imageData,fileData){
        imageData.forEach(file=>{
          let imagePush = {
          	fileId: this.generateImgId(),
          	file: file,
          	url: URL.createObjectURL(file)
          };
          this.imageData.push(imagePush);
        })
        fileData.forEach(file=>{
          let filePush = {
            fileId: this.generateId(),
            file: file,
          };
          this.fileData.push(filePush);
        })
      },
      onFileChange(event) {
        const file = event.target.files[0];
        if (file) {
          if (file.type.indexOf('image') !== -1) {
          	let imagePush = {
          		fileId: this.generateImgId(),
          		file: file,
          		url: URL.createObjectURL(file)
          	};
            this.imageData.push(imagePush);
          }else{
            let filePush = {
              fileId: this.generateId(),
              file: file,
            };
            this.fileData.push(filePush);
          }
        }
      },
      generateImgId(){
        return this.currentImgId++;
      },
      generateFileId(){
        return this.currentFileId++;
      },
      generateOtherId(){
        return this.currentOtherId++;
      },
      deleteFile(data,index){
        data.splice(index,1);
      },
      showRecordBox() {
        this.showRecord = true;
      },
      closeRecordBox() {
        this.showRecord = false;
      },
      showVideoBox() {
        this.showVideo = true;
      },
      closeVideoBox() {
        this.showVideo = false;
      },
      // 视频号、录音文件
      onSendData(data, fileType) {
        console.log(data)
        let name = fileType == 3 ? data.url.split('/').pop() : data.desc;
        //执行下loadGroup
        let msgInfo = {
          name: name,
          type: fileType,
          content: JSON.stringify(data),
        }

        let otherPush = {
          fileId: this.generateOtherId(),
          file: msgInfo,
        };
        this.otherData.push(otherPush);
        // 关闭窗口
        this.showVideo = false;
        this.showRecord = false;
      },

      notifySend() {
        this.$refs.chatInputEditor.submit();
      },
      async sendMessage(fullList) {

        //处理一下fullList
        if(this.imageData && this.imageData.length > 0){
          this.imageData.forEach(image =>{
            let data = {
              type: "image",
              content: image
            };
            if(!fullList){
              fullList = [];
            }
            fullList.push(data);
          })
        }
        if(this.fileData && this.fileData.length > 0){
          this.fileData.forEach(file =>{
            let data = {
              type: "file",
              content: file
            };
            if(!fullList){
              fullList = [];
            }
            fullList.push(data);
          })
        }

        this.modelList = [];
        if(this.otherData && this.otherData.length > 0){
          this.otherData.forEach(file =>{
             this.modelList.push(file.file);
          })
        }

        if((!((fullList && fullList.length > 0) || (this.modelList && this.modelList.length > 0)))){
          return this.$message.warning("请至少填写一项内容！")
        }

        let accountIds = this.accountIds && this.accountIds.length > 0 ? this.accountIds : this.checkAccountIds;
        if(!accountIds || accountIds.length <= 0){
          return this.$message.warning("账号未选中！")
        }
        this.submitLoading = true;
        this.resetEditor();

        //执行下loadGroup
        // if (this.$store.state.chatStore.syncTask != 'true' && this.$store.state.chatStore.isSync != true) {
        //   this.$store.commit("updateSyncTask", "true");
        // } else {
        //   this.$message.warning("群发任务正在处理中，请稍后。")
        //   return;
        // }

        // 执行中 转圈圈
        //处理只上传一次文件或图片
        await this.setFileData(fullList);

        let sendText = "";
        let fileType = 2;
        for (let i = 0; i < fullList.length; i++) {
          let msg = fullList[i];
          switch (msg.type) {
            case "text":
              await this.sendTextMessage(sendText + msg.content, msg.atUserIds);
              break;
            case "image":
              if (msg.fileData || msg.errorData) {
                await this.sendImageMessage(msg.content.file, msg.fileData, msg.errorData);
              }
              break;
            case "video":
              if (msg.fileData || msg.errorData) {
                await this.sendFileMessage(msg.content.file, msg.fileData, msg.errorData, 4);
              }
              break;
            case "file":
              if (msg.fileData || msg.errorData) {
                //判断下是否是录音文件
                if (msg.content.file && (msg.content.file.type == 'audio/wav' || msg.content.file.type ==
                    'audio/mpeg')) {
                  fileType = 3;
                } else if (msg.content.file && (msg.content.file.type == 'video/mp4' || msg.content.file.type ==
                    'video/mov')) {
                  fileType = 4;
                }
                await this.sendFileMessage(msg.content.file, msg.fileData, msg.errorData, fileType);
              }
              break;
          }
        }
        let param = {
          code: this.showType,
          keyword: this.keyWord,
          ids: accountIds,      //发送人的账号Id
          friendIds: this.friendIds, //接收人的uId
          friendUsers: this.friendUsers,//接收人的Id-uid
          title: this.title,
          topic: this.topic,
          fullList: this.modelList
        };
        console.log(param)
        handleUser(param).then(res=>{
          console.log(res)
          this.submitLoading = false;
        })

        this.submitLoading = false;
        // 关闭 转圈圈
        this.$emit("close");
      },

      async setFileData(fullList) {
        for (let i = 0; i < fullList.length; i++) {
          let msg = fullList[i];
          if (msg.type == "image") {
            let formData = new FormData()
            formData.append('file', msg.content.file)
            await this.$http.post("/system/im/image/upload", formData, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }).then((data) => {
              this.$set(msg, "fileData", data)
            }).catch((res) => {
              this.$set(msg, "errorData", res)
            })
          } else if (msg.type == "file") {
            let file = msg.content.file;
            let check = this.$refs.fileUpload.beforeUpload(file);
            if (check) {
              let retObj = await this.$refs.fileUpload.onSyncFileUpload({
                file
              });
              if (retObj.data) {
                this.$set(msg, "fileData", retObj)
              } else {
                this.$set(msg, "errorData", retObj)
              }
            }
          }
        }
      },
      sendImageMessage(file, fileData, errorData) {
        this.onImageBefore(file);
        let msgInfo = JSON.parse(JSON.stringify(file.msgInfo));
        msgInfo.content = JSON.stringify(fileData);
        //存入对象
        this.modelList.push(msgInfo);
      },

      onImageBefore(file) {
        let url = URL.createObjectURL(file);
        let data = {
          originUrl: url,
          thumbUrl: url
        }
        let msgInfo = {
          id: 0,
          tmpId: this.generateId(),
          fileId: file.uid,
          sendId: this.mine.id,
          content: JSON.stringify(data),
          sendTime: new Date().getTime(),
          selfSend: true,
          type: 1,
          readedCount: 0,
          loadStatus: "loading",
          status: this.$enums.MESSAGE_STATUS.UNSEND
        }
        file.msgInfo = msgInfo;
      },

      async sendFileMessage(file, fileData, errorData, fileType) {
        let fileMsg = JSON.parse(JSON.stringify(fileData))
        //file 解耦
        this.onFileBefore(file, fileMsg, fileType)
        if (fileData) {
          await this.onFileSuccess(fileData.data, fileData.file, fileMsg, fileType);
        } else {
          this.onFileFail(errorData.error, errorData.file);
        }
      },

      onFileBefore(file, fileMsg, fileType) {
        let url = URL.createObjectURL(file);
        let data = {
          name: file.name,
          size: file.size,
          url: url
        }
        let msgInfo = {
          id: 0,
          tmpId: this.generateId(),
          sendId: this.mine.id,
          content: JSON.stringify(data),
          sendTime: new Date().getTime(),
          selfSend: true,
          type: fileType,
          loadStatus: "loading",
          readedCount: 0,
          status: this.$enums.MESSAGE_STATUS.UNSEND
        }
        // 借助file对象透传
        fileMsg.msgInfo = msgInfo;
      },

      async onFileSuccess(url, file, fileMsg, fileType) {
        if (4 == fileType) {
          await this.getVideoFirstFrame(url).then(async res => {
            if (res) {
              // await basePngApi({
              //   base64: res
              // }).then(base => {
              //   if (base.code == 200) {
              //     this.handleFile(url, file, fileMsg, base.msg, null)
              //   }
              // })
            }
          })
        } else if (3 == fileType) {
          try {
            const duration = await this.getAudioDuration(file);
            // 在获取到时长后处理文件
            this.handleFile(url, file, fileMsg, null, duration);
          } catch (error) {
            console.error('音频加载失败:', error);
          }
        } else {
          this.handleFile(url, file, fileMsg, null, null)
        }
      },
      getAudioDuration(file) {
        return new Promise((resolve, reject) => {
          const audio = new Audio();
          audio.src = URL.createObjectURL(file);
          // 监听 loadedmetadata 事件
          audio.onloadedmetadata = () => {
            if (audio.duration) {
              resolve(audio.duration); // 返回音频时长
            } else {
              reject(new Error('无法获取音频时长'));
            }
          };
          // 监听错误事件
          audio.onerror = () => {
            reject(new Error('音频加载失败，请检查URL和格式是否正确'));
          };
        });
      },

      handleFile(url, file, fileMsg, imgUrl, duration) {
        let data = {
          name: file.name,
          size: file.size,
          url: url
        }
        if (imgUrl) {
          this.$set(data, "videoImgUrl", imgUrl)
        }
        if (duration) {
          this.$set(data, "duration", duration)
        }
        let msgInfo = JSON.parse(JSON.stringify(fileMsg.msgInfo));
        msgInfo.content = JSON.stringify(data);
        //存入对象
        this.modelList.push(msgInfo);
      },

      onFileFail(e, file) {
        let msgInfo = JSON.parse(JSON.stringify(file.msgInfo));
        msgInfo.loadStatus = 'fail';
        this.$store.commit("insertMessage", msgInfo);
      },


      sendTextMessage(sendText, atUserIds) {
        if (!sendText.trim()) {
          reject();
        }
        let msgInfo = {
          content: sendText,
          type: 0,
        }
        //存入对象
        this.modelList.push(msgInfo);
      },

      closeRefBox() {
        this.$refs.emoBox.close();
      },
      showEmotionBox() {
        let width = this.$refs.emotion.offsetWidth;
        let left = this.$elm.fixLeft(this.$refs.emotion);
        let top = this.$elm.fixTop(this.$refs.emotion);
        this.$refs.emoBox.open({
          x: left + width / 2,
          y: top + 234 + 36
        })
      },
      onEmotion(emoText) {
        this.$refs.chatInputEditor.insertEmoji(emoText);
      },
      resetEditor() {
        this.$nextTick(() => {
          if (this.$refs.chatInputEditor) {
            this.$refs.chatInputEditor.clear();
            this.$refs.chatInputEditor.focus();
          }
        });
      },
      generateId() {
        // 生成临时id
        return String(new Date().getTime()) + String(Math.floor(Math.random() * 1000));
      },

      handleGenerate(type){
        if(this.keyWord == undefined || this.keyWord == ""){
          return this.$message.warning("关键词不能为空！");
        }
        if(type == '文案'){
          this.waLoading = true;
        }else if(type == '图文'){
          this.tpLoading = true;
        }else{
          this.spLoading = true;
        }

        let param = {
          type: type,
          keyword: this.keyWord,
          platform: this.showPlatform
        };
        console.log(param)
        generateWork(param).then((data) =>{
          let res = data.data;
          if(res){
            let outputs = res.outputs;
            if(outputs){
             let text = outputs.文案;
             console.log(text);
             this.$refs.chatInputEditor.openHtml(text);
            }else{
              this.$message.warning("文案生成失败！");
            }
          }else{
              this.$message.warning("文案生成失败！");
            }
          this.closeBtnLoad();
        }).catch(err=>{
          this.closeBtnLoad();
        })
        // setTimeout(()=>{
        //   let jsonstr = {
        //       "id": "0fe1c740-79b5-4887-84c1-8a632b54b3b9",
        //       "workflow_id": "2645fce5-2037-4eff-b5c6-7edae773cd0d",
        //       "status": "succeeded",
        //       "outputs": {
        //           "文案": "🍂你以为秋天只有落叶和毛衣？  \n🔥这些隐藏知识点我赌你搜过：  \n1.栗子裂开才甜？🌰温度说了算！  \n2.桂花香能保存？🧊-18℃锁住秋天  \n3.「贴秋膘」最早是...（古人比你懂养生！）  \n\n💡刷到就是大数据在提醒：  \n冷门知识+热乎糖炒栗子=完美秋日仪式感  \n\n📚评论区交出你的「秋季迷惑行为」  \n让我看看还有多少人在搜：  \n「落叶踩起来脆不脆和含水量有关吗」  \n\n#秋天冷知识 #万物皆可检索 #这个知识点终于火了"
        //       },
        //       "elapsed_time": 32.11162352212705,
        //       "total_tokens": 669,
        //       "total_steps": 7,
        //       "created_at": 1744787506,
        //       "finished_at": 1744787538
        //   };
        //   let res = JSON.parse(JSON.stringify(jsonstr));
        //   let outputs = res.outputs;
        //   let text = outputs.文案;
        //   console.log(text);
        //   this.$refs.chatInputEditor.openHtml(text);
        //   this.closeBtnLoad();
        // },1000)

      },
      closeBtnLoad(){
        this.waLoading = false;
        this.tpLoading = false;
        this.spLoading = false;
      }
    }
  }
</script>

<style lang="scss">

  ::v-deep .el-dialog__header{
    padding-bottom: 0px !important;
  }



  .mass-input {
    width: 100%;
    height: 320px;
    border: 1px solid #EEEEEE;

    // background: #F7F7F7;
    // position: relative;
    // .chat-input-area {
    //   height: 88%;
    //   background: #EEE;
    // }
    // .chat-tool-bar{
    //   display: flex;
    //   flex-direction: row;
    //   align-items: center;
    //   justify-content: center;
    //   position: absolute;
    //   bottom: 5px;
    //   left: 5px;
    // }
    .mass-desc {
      width: 100%;
      height: 35px;
      display: flex;
      align-items: center;
      padding: 0px 10px;
    }
  }

  .im-chat-footer {
    display: flex;
    flex-direction: column;
    padding: 0;

    .chat-tool-bar {
      display: flex;
      position: relative;
      width: 100%;
      height: 36px;
      text-align: left;
      box-sizing: border-box;
      border-top: var(--im-border);
      padding: 4px 2px 2px 8px;

      .file-video,
      .file-audio {
        display: flex;
        align-items: center;
        justify-content: center;

        .el-upload {
          display: flex;
        }
      }

      >div {
        font-size: 22px;
        cursor: pointer;
        line-height: 30px;
        width: 30px;
        height: 30px;
        text-align: center;
        border-radius: 2px;
        margin-right: 8px;
        color: #999;
        transition: 0.3s;

        &.chat-tool-active {
          font-weight: 600;
          color: var(--im-color-primary);
          background-color: #ddd;
        }
      }

      >div:hover {
        color: #333;
      }
    }

    .send-content-area {
      position: relative;
      display: flex;
      flex-direction: column;
      height: 100%;
      background-color: white !important;

      .send-text-area {
        box-sizing: border-box;
        padding: 5px;
        width: 100%;
        flex: 1;
        resize: none;
        font-size: 16px;
        outline: none;
        text-align: left;
        line-height: 30px;

        &:before {
          content: attr(placeholder);
          color: gray;
        }

        .at {
          color: blue;
          font-weight: 600;
        }

        .receipt {
          color: darkblue;
          font-size: 15px;
          font-weight: 600;
        }

        .emo {
          width: 30px;
          height: 30px;
          vertical-align: bottom;
        }
      }

      .send-image-area {
        text-align: left;
        border: #53a0e7 solid 1px;

        .send-image-box {
          position: relative;
          display: inline-block;

          .send-image {
            max-height: 180px;
            border: 1px solid #ccc;
            border-radius: 2%;
            margin: 2px;
          }

          .send-image-close {
            position: absolute;
            padding: 3px;
            right: 7px;
            top: 7px;
            color: white;
            cursor: pointer;
            font-size: 15px;
            font-weight: 600;
            background-color: #aaa;
            border-radius: 50%;
            border: 1px solid #ccc;
          }
        }
      }

      .send-btn-area {
        padding: 10px;
        position: absolute;
        bottom: 4px;
        right: 6px;
      }
    }
  }

  .tag-border {
    padding: 10px 20px 20px;
    overflow-y: scroll;
    border-bottom: 1px solid #F1F3F4;

    .tag-item {
      display: flex;
      flex-direction: column;
      align-items: flex-start;

      .order-tag {
        margin: 0px 10px 10px 0px;
        cursor: pointer;
      }
    }
  }

  .file-border{
    ::v-deep .el-link--inner{
      width: 100%;
    }
    .yichu{
      width: 70%;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break:break-all;
    }
    .upload-file-uploader {
      margin-bottom: 5px;
    }
    .upload-file-list .el-upload-list__item {
      border: 1px solid #e4e7ed;
      // line-height: 2;
      margin-bottom: 10px;
      position: relative;
    }
    .upload-file-list .ele-upload-list__item-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      color: inherit;
    }
    .ele-upload-list__item-content-action .el-link {
      margin-right: 10px;
    }
  }
</style>
