module.exports = function(grunt) {

  var crypto = require('crypto');
  var fs = require('fs');

  //对随机16位数字加密
  var hashTmp = crypto.createHash('md5').update(String(Math.random() * 10000000000000000)).digest('hex');
  var randomNum = hashTmp.slice(0, 6);
  var buidPath = 'build/release-' + randomNum;
  var release_path = 'build_release';
  var concat_css_file_path = buidPath + '/css/<%= pkg.name %>-<%= pkg.version %>.css';
  var concat_js_file_path = buidPath + '/js/<%= pkg.name %>-<%= pkg.version %>.js';
  grunt.log.writeln("===============" + randomNum + "===========");

  grunt.log.writeln("==========================");
  grunt.log.writeln("==========================");
  grunt.log.writeln("grunt自动脚本开始运行......");
  grunt.log.writeln("==========================");
  grunt.log.writeln("==========================");
  grunt.log.writeln("====请稍后，先观赏神兽====");
  grunt.log.writeln(" ");
  grunt.log.writeln(" ");
  grunt.log.writeln(" ");
  grunt.log.writeln("上帝的骑宠，上古时期世界的霸主。");

  grunt.log.writeln("┏┛┻━━━┛┻┓");
  grunt.log.writeln("┃｜｜｜｜｜｜｜┃");
  grunt.log.writeln("┃　　　━　　　┃");
  grunt.log.writeln("┃　┳┛ 　┗┳ 　┃");
  grunt.log.writeln("┃　　　　　　　┃");
  grunt.log.writeln("┃　　　┻　　　┃");
  grunt.log.writeln("┃　　　　　　　┃");
  grunt.log.writeln("┗━┓　　　┏━┛");
  grunt.log.writeln("　　┃　史　┃　　");
  grunt.log.writeln("　　┃　诗　┃　　");
  grunt.log.writeln("　　┃　之　┃　　");
  grunt.log.writeln("　　┃　宠　┃");
  grunt.log.writeln("　　┃　　　┗━━━┓");
  grunt.log.writeln("　　┃经验与我同在　┣┓");
  grunt.log.writeln("　　┃攻楼专用宠物　┃");
  grunt.log.writeln("　　┗┓┓┏━┳┓┏┛");
  grunt.log.writeln("　　　┃┫┫　┃┫┫");
  grunt.log.writeln("　　　┗┻┛　┗┻┛");
  grunt.log.writeln(" ");
  grunt.log.writeln(" ");
  grunt.log.writeln(" ");

  grunt.log.writeln("任务执行详情：");
  grunt.log.writeln("");


  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    buildConfig: {
      randomNum: randomNum,
      path: buidPath
    },
    clean: {
      all: ['build', 'build_tmp', 'build_zip', release_path]
    },
    less: {
      development: {
        options: {
          compress: false
        },
        files: {
          "css/element.uncompressed.css": "less/element/*.less",
          "css/main.uncompressed.css": "less/main/*.less"
        }
      },
      production: {
        options: {
          compress: true,
          optimization: 2,
          cleancss: true
        },
        files: {
          // "css/app.css": "less/app.less"
        }
      }
    },
    sass: { // Task
      dist: { // Target
        files: { // Dictionary of files
          'css/style-element.css': 'sass/element/*.scss'
        },
        options: {
          sourcemap: 'true'
        }
      }
    },
    watch: {
      styles: {
        files: ['less/element/*.less', 'less/main/*.less'],
        tasks: ['less'],
        options: {
          nospawn: true
        }
      }
    },
    filerev: {
      options: {
        encoding: 'utf8',
        algorithm: 'md5',
        length: 6
      },
      js: {
        src: [buidPath + '/js/*.js'],
        dest: buidPath + '/js'
      },
      css: {
        src: buidPath + '/css/*.css',
        dest: buidPath + '/css'
      }
      /*  tpl: {
          src: 'js/**/
      /*template/*.tpl',
              dest: buidPath + '/tpl'
            }*/
    },
    jst: { //压缩模板
      compile: {
        options: {
          amd: true,
          templateSettings: {
            interpolate: /\{\{(.+?)\}\}/g,
            variable: 'data'
          },
          prettify: true
        },
        files: { //输出压缩后的文件
          "<%=buildConfig.path%>/js/<%= pkg.name %>-<%= pkg.version %>-templates.js": ["js/longyan/template/*.tpl", "js/element/template/*.tpl"]
        }
      }
    },
    // watch: { //监视文件修改(未生效
    //   scripts: {
    //     files: 'build/*.*',
    //     tasks: ['jst'],
    //     options: {
    //       interrupt: true,
    //     },
    //   },
    // },
    concat: {
      options: {
        banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n' //默认banner
      },
      css: { //css合并配置
        //src: ['css/app.uncompressed.css'], //当前grunt项目中路径下的src/css目录下的所有css文件  
        //dest: 'build/css/<%= pkg.name %>-<%= pkg.version %>.css' //生成到grunt项目路径下的dist文件夹下为all.css  
        files: {
          'build/release-<%=buildConfig.randomNum%>/css/<%= pkg.name %>-<%= pkg.version %>.css': [
            'css/element.*.css',
            'css/main.*.css',
            'css/demo.css',
            'css/iconfont.css',
            'css/picker.css',
            'css/ion.rangeSlider.css',
            'css/ion.rangeSlider.skinNice.css'
          ]
        }
      },
      js: { //js合并配置
        files: {
          'build/release-<%=buildConfig.randomNum%>/js/<%= pkg.name %>-<%= pkg.version %>.js': [
            // 'build/<%= pkg.name %>-<%= pkg.version %>-templates.js', //合并后的模板文件
            '<%=buildConfig.path%>/js/<%= pkg.name %>-<%= pkg.version %>-templates.js',
            'js/util/**/*.js',
            'js/components/*.js',
            'js/api/*.js',
            'js/longyan/view/*.js',
            'js/element/view/*.js',
            'js/router.js'
          ]
        }
      },
      uijs: {
        src: [
          'lib/jquery/1.11.2/jquery.js',
          'lib/flexslider/jquery.flexslider.js',
          'lib/main.js'
        ],
        dest: 'lib/app.uncompressed.js'
      }
    },
    cssmin: { //css文件压缩  
      css: {
        src: buidPath + '/css/<%= pkg.name %>-<%= pkg.version %>.css', //将之前的all.css  
        dest: buidPath + '/css/<%= pkg.name %>-<%= pkg.version %>.min.css' //压缩  
      },
      css2: {
        src: buidPath + '/css/<%= pkg.name %>-<%= pkg.version %>.css', //将之前的all.css  
        dest: release_path + '/css/<%= pkg.name %>-<%= pkg.version %>.min.css' //压缩  
      }
    },
    uglify: { //js文件压缩
      build: {
        // files: {
        src: buidPath + '/js/<%= pkg.name %>-<%= pkg.version %>.js', //压缩源文件是之前合并的buildt.js文件  
        dest: buidPath + '/js/<%= pkg.name %>-<%= pkg.version %>.min.js' //压缩  
          //  'build/js/<%= pkg.name %>-<%= pkg.version %>.min.js': ['build/js/<%=pkg.name%>-<%=pkg.version%>.js'],
          //  'build/release-' + randomNum + '/js/<%= pkg.name %>-<%= pkg.version %>.min.js': ['build/js/<%=pkg.name%>-<%=pkg.version%>.js'],
          //  }
      },
      build2: {
        // files: {
        src: buidPath + '/js/<%= pkg.name %>-<%= pkg.version %>.js', //压缩源文件是之前合并的buildt.js文件  
        dest: release_path + '/js/<%= pkg.name %>-<%= pkg.version %>.min.js' //压缩  
          //  'build/js/<%= pkg.name %>-<%= pkg.version %>.min.js': ['build/js/<%=pkg.name%>-<%=pkg.version%>.js'],
          //  'build/release-' + randomNum + '/js/<%= pkg.name %>-<%= pkg.version %>.min.js': ['build/js/<%=pkg.name%>-<%=pkg.version%>.js'],
          //  }
      }
    },
    usemin: {
      //  js: 'build/js/*.js',
      html: ['../longyan.jsp'],
      // options: {
      //   assetsDirs: ['build/js'],
      //   patterns: {
      //     js: [
      //       [/(\/js\/[\/\w-]+\.js)/, 'replace image in js']
      //     ]
      //   }
      // }
      options: {

        blockReplacements: {
          // tpl: function(block) {
          //   console.log('tpl......');
          //   console.log(block.dest);
          //   return '\'text!js/user/template/' + block.dest + '\'';
          // },
          css: function(block) {
            var real_path = block.dest;

            real_path = real_path.replace('build', 'build/release-' + randomNum);

            console.log(real_path);
            var file = '../' + real_path + '.css';
            var hash = crypto.createHash('md5').update(fs.readFileSync(file)).digest('hex');
            var suffix = hash.slice(0, 6);
            console.log('suffix=' + suffix);
            var final_name = real_path + '.' + suffix + '.css';
            console.log(final_name);
            return '<!-- build:css ' + block.dest + '-->\n' +
              '\t\t<link rel="stylesheet" href="<%=systemConfig.get("staticLongyanUrl")%>/' + final_name.replace("static_longyan_app/", "") + '">\n' +
              ' <!-- endbuild -->';
          },
          js: function(block) {
            var real_path = block.dest;
            console.log("real_path" + real_path);
            if (real_path.indexOf("build/lib") >= 0) {
              console.log(real_path);
              real_path = real_path.replace('build', 'build/release-' + randomNum);
              return '<!-- build:js ' + block.dest + ' -->\n' +
                '\t\t<script src="' + real_path + '"></script>\n' +
                ' <!-- endbuild -->';
            } else {
              real_path = real_path.replace('build', 'build/release-' + randomNum);

              console.log(real_path);
              var file = '../' + real_path + '.js';
              var hash = crypto.createHash('md5').update(fs.readFileSync(file)).digest('hex');
              var suffix = hash.slice(0, 6);
              console.log('suffix=' + suffix);
              var final_name = real_path + '.' + suffix + '.js';
              console.log(final_name);
              return '<!-- build:js ' + block.dest + ' -->\n' +
                '\t\t<script src="<%=systemConfig.get("staticLongyanUrl")%>/nvwa-loader-1.7.0.js"\n' +
                '\t\tapi = ""\n' +
                '\t\tbaseUrl = "build/release-' + randomNum + '/js"\n' +
                '\t\tskin = ""\n' +
                '\t\tdebug = "true"\n' +
                '\t\tlang = "zh_CN"\n' +
                '\t\tjsonp = "true"\n' +
                '\t\tstaticDomain = "<%=systemConfig.get("staticDomain")%>"\n' +
                '\t\tpreload = "<%=systemConfig.get("staticLongyanUrl")%>/build/release-' + randomNum + '/js/' + final_name.split('/')[final_name.split('/').length - 1] + ',bootstrap.min,jquery.ui.widget,fileUpload" > </script>\n' +
                ' <!-- endbuild -->';
            }
          }
        }
      }
    },
    copy: {
      main: {
        files: [{ //拷贝lib素材
            src: ['lib/**'],
            dest: buidPath + '/'
          }, { //拷贝css素材
            src: ['css/**'],
            dest: buidPath + '/'
          }, { //拷贝image素材
            src: ['img/*', 'img/*/*'],
            dest: buidPath + '/'
          }, { //拷贝image素材
            src: ['css/rangeSlider/*.png'],
            dest: buidPath + '/'
          }, { //拷贝index.js
            src: ['js/index.js'],
            dest: buidPath + '/'
          }, { //拷贝fonts素材
            src: ['fonts/*'],
            dest: buidPath + '/'
          }, { //拷贝photo素材插件
            src: ['js/util/photo/*'],
            dest: buidPath + '/'
          }, {
            src: ['nvwa-loader-1.7.0.js'],
            dest: release_path + '/'
          }, {
            src: ['bridge.js'],
            dest: release_path + '/'
          }, {
            src: ['release_index.html'],
            dest: release_path + '/'
          }, { //拷贝lib素材
            src: ['lib/**'],
            dest: release_path + '/'
          }, { //拷贝image素材
            src: ['img/*', 'img/*/*'],
            dest: release_path + '/'
          }, { //拷贝image素材
            src: ['css/rangeSlider/*.png'],
            dest: release_path + '/'
          }, { //拷贝index.js
            src: ['js/index.js'],
            dest: release_path + '/'
          }, { //拷贝fonts素材
            src: ['fonts/*'],
            dest: release_path + '/'
          }
          // , {
          //   src: [buidPath + '/**/*'],
          //   dest: 'build_tmp/'
          // }
          /*, { //拷贝view的模板文件
                      src: ['js/user/template/*'],
                      dest: buidPath + '/'
                    }
                    , { //拷贝view的模板文件
                              src: ['js/util/template/*'],
                              dest: buidPath + '/'
                            }*/
        ]
      }
    },
    // copy2: {
    //   main: {
    //     files: [{
    //       src: ['build/release-' + randomNum + '/**/*'],
    //       dest: 'build_tmp'
    //     }]
    //   }
    // },
    compress: {
      main: {
        options: {
          archive: 'build_zip/release-' + randomNum + '.zip'
        },
        files: [{
          src: [release_path + '/**/*'],
          dest: '/',
          filter: 'isFile'
        }]
      }
    }
  });
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-text-replace');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-mkdir');
  grunt.loadNpmTasks('grunt-contrib-compress');
  grunt.loadNpmTasks('grunt-svn-export');
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-jst');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-shell');
  grunt.loadNpmTasks('grunt-filerev');
  grunt.loadNpmTasks('grunt-usemin');
  // grunt.loadNpmTasks('grunt-shell');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-autoprefixer');



  // 默认被执行的任务列表。
  grunt.registerTask('default', [1]);

  //执行less, concat, uglify
  grunt.registerTask('ui', ['less']);

  //执行js合并压缩任务
  grunt.registerTask('js', ['jst', 'concat:js', 'uglify']);
  //执行css合并压缩任务
  grunt.registerTask('css', ['less', 'concat:css', 'cssmin']);
  //执行任务
  grunt.registerTask('app', ['clean', 'js', 'css', 'filerev', 'copy', 'usemin', 'compress']);
};