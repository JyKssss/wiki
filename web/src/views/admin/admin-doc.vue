<template>

  <a-layout>
    <a-layout-content
        :style="{ background: '#ffffff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form
            layout="inline"
            :model="param"
        >
          <a-form-item>
            <a-input v-model:value="param.name" placeholder="名称">
<!--              <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>-->
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button
                type="primary"
                @click="handleQuery()"
            >
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()" >
              新增
            </a-button>
          </a-form-item>

        </a-form>



      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="level1"
          :loading="loading"
          :pagination="false"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>

        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
               编辑
            </a-button>

            <a-popconfirm
                title="删除后无法恢复 确认删除?"
                ok-text="Yes"
                cancel-text="No"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger" >
                删除
              </a-button>
            </a-popconfirm>


          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <a-modal
      title="文档表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="doc" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="名称">
        <a-input v-model:value="doc.name" />
      </a-form-item>

      <a-form-item label="父文档">
        <a-tree-select
            v-model:value="doc.parent"
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            :tree-data="treeSelectData"
            placeholder="请选择父文档"
            tree-default-expand-all
            :replaceFields="{title:'name', key:'id', value: 'id'}"
        >
        </a-tree-select>
      </a-form-item>

      <a-form-item label="顺序">
        <a-input v-model:value="doc.sort" />
      </a-form-item>

      <a-form-item label="内容">
        <div id="content"></div>
      </a-form-item>
    </a-form>
  </a-modal>

</template>

<script lang="ts">
import {createVNode, defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import { Modal } from 'ant-design-vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import E from 'wangeditor';

export default defineComponent({
  name: 'AdminDoc',
  setup() {
    const route= useRoute();
    const param =ref();
    param.value={};
    const docs = ref();
    const loading = ref(false);

    const columns = [
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父文档',
        key: 'parent',
        dataIndex: 'parent'
      },
      {
        title: '顺序',
        key: 'sort',
        dataIndex: 'sort'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];


    /**
     * 一级文档树 children是二级文档
     * [{
     *   id:"",
     *   name:"",
     *   children:[{
     *     id:"",
     *     name:"",
     *   }]
     * }]
     *
     */
    const level1=ref();//一级文档

    /**
     * 数据查询
     **/
    const handleQuery = () => {
      loading.value = true;
      //如果不清空现有数据 则编辑保存后再点击编辑还会出现旧数据
      level1.value=[];
      axios.get("/doc/all",{
          params:{
            name:param.value.name
          }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success){
          docs.value = data.content;
          console.log("原始数据： ",docs.value);

          level1.value=[];
          level1.value=Tool.array2Tree(docs.value,0);
          console.log("树形结构数据： ",level1);
        }
        else {
          message.error(data.message);
        }

      });
    };



    //----------表单-------------
    //树选择组件的状态会随着当前编辑的节点状态变化而变化 单独声明变量处理level1数据
    const treeSelectData=ref();
    treeSelectData.value= [];
    const doc=ref({});
    const modalVisible=ref(false);
    const modalLoading=ref(false);
    const editor= new E("#content");

    const handleModalOk= () => {
      modalLoading.value = true;

      axios.post("/doc/save", doc.value).then((response) => {

        modalLoading.value = false;
        const data = response.data;
        if (data.success){
          modalVisible.value = false;

          //重新加载列表数据
          handleQuery();
        }
        else {
          message.error(data.message);
        }
      });
    };

    /**
     * 将某节点及其子孙节点都置为Disabled不可选状态
     */
    const setDisable =(treeSelectData: any, id: any) =>{
      //层级遍历
      for (let i = 0;i<treeSelectData.length; i++){
        const node=treeSelectData[i];
        //判断是不是目标节点
        if (node.id === id){
          console.log("disabled",node);

          node.disabled =true;

          //遍历所有子节点 并disabled
          const children= node.children;
          if (Tool.isNotEmpty(children)){
            for (let j = 0; j < children.length; j++) {
              setDisable(children,children[j].id);
            }
          }
        }
        else {
          //不是目标节点
          const children= node.children;
          if (Tool.isNotEmpty(children)){
            setDisable(children,id);
          }
        }
      }
    }

    const ids: Array<String>=[];
    const deleteNames: Array<String>=[];
    /**
     * 将某节点及其子孙节点都加入删除List ids
     */
    const getDeleteIds =(treeSelectData: any, id: any) =>{
      //层级遍历
      for (let i = 0;i<treeSelectData.length; i++){
        const node=treeSelectData[i];
        //判断是不是目标节点
        if (node.id === id){
          console.log("delete",node);

          node.disabled =true;
          ids.push(id)
          deleteNames.push(node.name);
          //遍历所有子节点 并加入删除List
          const children= node.children;
          if (Tool.isNotEmpty(children)){
            for (let j = 0; j < children.length; j++) {
              getDeleteIds(children,children[j].id);
            }
          }
        }
        else {
          //不是目标节点
          const children= node.children;
          if (Tool.isNotEmpty(children)){
            getDeleteIds(children,id);
          }
        }
      }
    }


    /**
     * 编辑
     */

    const edit= (record: any) =>{
      modalVisible.value=true;
      doc.value=Tool.copy(record);

      //不能选择当前阶段及其子节点
      treeSelectData.value= Tool.copy(level1.value);
      setDisable(treeSelectData.value, record.id);

      //为树添加一个“无”节点
      treeSelectData.value.unshift({id:0, name:'无'});
      setTimeout(function () {
        editor.create();
      },100);

    }

    /**
     * 新增
     */

    const add= () =>{
      modalVisible.value=true;
      doc.value= {
        ebookId: route.query.ebookId
      };

      treeSelectData.value= Tool.copy(level1.value);
      //为树添加一个“无”节点
      treeSelectData.value.unshift({id:0, name:'无'});
      setTimeout(function () {
        editor.create();
      },100);
    }

    /**
     * 删除
     */

    const handleDelete= (id: number) =>{
      console.log(level1,level1.value,id);
      ids.length=0;
      deleteNames.length = 0;
      getDeleteIds(level1.value,id);
      Modal.confirm({
        title: '重要提醒！',
        icon: createVNode(ExclamationCircleOutlined),
        content: '即将删除：【'+deleteNames.join(",")+'】 删除后不可恢复，是否确认删除？',
        onOk() {
          axios.delete("/doc/delete/"+ids.join(",")).then((response) => {

            const data = response.data;
            if (data.success){

              //重新加载列表数据
              handleQuery();
            }else {
              message.error(data.message);
            }
          });
        },
      });
    }


    onMounted(() => {
      handleQuery();
    });

    return {
      param,
      // docs,
      level1,
      columns,
      loading,
      handleQuery,

      edit,
      add,

      doc,
      modalVisible,
      modalLoading,
      handleModalOk,
      handleDelete,
      treeSelectData
    }

  }
})
</script>


